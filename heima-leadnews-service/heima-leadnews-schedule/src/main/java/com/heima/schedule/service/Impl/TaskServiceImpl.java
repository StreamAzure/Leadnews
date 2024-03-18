package com.heima.schedule.service.Impl;

import com.alibaba.fastjson.JSON;
import com.heima.common.constants.ScheduleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.schedule.dtos.Task;
import com.heima.model.schedule.pojos.Taskinfo;
import com.heima.model.schedule.pojos.TaskinfoLogs;
import com.heima.schedule.mapper.TaskinfoLogsMapper;
import com.heima.schedule.mapper.TaskinfoMapper;
import com.heima.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskinfoMapper taskinfoMapper;
    @Autowired
    private TaskinfoLogsMapper taskinfoLogsMapper;
    @Autowired
    private CacheService cacheService;

    /**
     * 添加延迟任务
     * @param task
     * @return
     */
    @Override
    public long addTask(Task task) {
        // 1. 添加任务到数据库
        boolean success = addTaskToDb(task);
        if(success){
            // 2. 添加任务到Redis
            addTaskToCache(task);
        }
        return task.getTaskId();
    }

    @Override
    public boolean cancelTask(long taskId) {
        boolean flag = false;

        // 删除任务，更新任务日志
        Task task = updateDb(taskId, ScheduleConstants.CANCELLED);
        // 删除redis数据
        if(task!=null){
            removeTaskFromCache(task);
            flag = true;
        }
        return flag;
    }

    @Override
    public Task poll(int type, int priority) {
        Task task = null;
        try {
            // 从 Redis 中拉取数据 pop
            String key = type + "_" + priority;
            String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC + key);
            if (StringUtils.isNotBlank(task_json)) {
                task = JSON.parseObject(task_json, Task.class);
                // 修改数据库信息
                updateDb(task.getTaskId(), ScheduleConstants.EXECUTED);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("poll task exception");
        }
        return task;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh() {
        // 防止多个实例同时刷新：分布式锁
        String token  = cacheService.tryLock("FUTURE_TASK_SYNC", 1000*30); // 锁超时为30s
        if(StringUtils.isNotBlank(token)){
            // 获得锁成功
            log.info("未来数据定时刷新--定时任务");
            // 获取所有未来数据的集合 Key
            Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");
            for(String futureKey : futureKeys){
                // 获取当前数据的 topic
                String topicKey = ScheduleConstants.TOPIC + futureKey.split(ScheduleConstants.FUTURE)[1];

                // 按照 key 和分值查询符合条件的数据
                Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());
                // 同步数据
                if (!tasks.isEmpty()){
                    cacheService.refreshWithPipeline(futureKey, topicKey, tasks);
                    log.info("成功将"+ futureKey + "刷新到" + topicKey);
                }
            }
        }
    }


    /**
     * 删除Redis中的任务
     * @param task
     */
    private void removeTaskFromCache(Task task) {
        String key = task.getTaskType() + "_" + task.getPriority();
        if(task.getExecuteTime() <= System.currentTimeMillis()){ // 如果任务在消费队列中
            cacheService.lRemove(ScheduleConstants.TOPIC+key, 0, JSON.toJSONString(task));
        }
        else{ // 如果任务在未来数据队列中
            cacheService.zRemove(ScheduleConstants.FUTURE+key, JSON.toJSONString(task));
        }
    }

    private Task updateDb(long taskId, int status) {
        Task task = null;
        try {
            // 删除原任务
            taskinfoMapper.deleteById(taskId);

            // 更新任务日志
            TaskinfoLogs taskinfoLogs = taskinfoLogsMapper.selectById(taskId);
            taskinfoLogs.setStatus(status);
            taskinfoLogsMapper.updateById(taskinfoLogs);

            // 替换为新任务
            task = new Task();
            BeanUtils.copyProperties(taskinfoLogs, task);
            // Taskinfo中的executeTime字段和Task中的同名字段类型不同（Date和Long），需要手动复制过来
            task.setExecuteTime(taskinfoLogs.getExecuteTime().getTime());
        }catch (Exception e){
            log.error("task cancel exception taskId={}", taskId);
        }
        return task;
    }

    /**
     * 任务添加到Redis
     * @param task
     */
    private void addTaskToCache(Task task){
        // key字符串构造
        String key = task.getTaskType() + "_" + task.getPriority();

        // 获取5分钟后的时间，毫秒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        long nextScheduleTime = calendar.getTimeInMillis();

        // 任务需要立即执行
        if(task.getExecuteTime() <= System.currentTimeMillis()){
            // 加入到消费队列
            cacheService.lLeftPush(ScheduleConstants.TOPIC + key, JSON.toJSONString(task));
        } else if(task.getExecuteTime() <= nextScheduleTime){
            cacheService.zAdd(ScheduleConstants.FUTURE + key, JSON.toJSONString(task), task.getExecuteTime());
        }
    }

    /**
     * 添加任务到数据库中
     * @param task
     * @return
     */
    private boolean addTaskToDb(Task task) {
        boolean flag = false;

        try{
            // 保存任务表
            Taskinfo taskinfo = new Taskinfo();
            BeanUtils.copyProperties(task, taskinfo);
            taskinfo.setExecuteTime(new Date(task.getExecuteTime()));
            taskinfoMapper.insert(taskinfo);

            // 设置taskID
            task.setTaskId(taskinfo.getTaskId());

            // 保存任务日志数据
            TaskinfoLogs taskinfoLogs = new TaskinfoLogs();
            BeanUtils.copyProperties(taskinfo, taskinfoLogs);
            taskinfoLogs.setVersion(1);
            taskinfoLogs.setStatus(ScheduleConstants.SCHEDULED); // 标记状态为等待执行
            taskinfoLogsMapper.insert(taskinfoLogs);

            flag = true;
        } catch (Exception e){
            log.info("任务插入到DB时异常：" + e.getMessage());
            throw new RuntimeException("任务插入到DB时异常，手动抛出 RuntimeException 以便事务回滚");
        }
        return flag;
    }
}
