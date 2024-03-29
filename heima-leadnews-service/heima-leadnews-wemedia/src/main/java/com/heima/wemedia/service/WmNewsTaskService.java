package com.heima.wemedia.service;

import com.heima.apis.article.IScheduleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

public interface WmNewsTaskService {

    /**
     * 添加任务到发布队列中
     * @param id            文章ID
     * @param publishTime   发布时间
     */
    public void addNewsToTask(Integer id, Date publishTime);

    /**
     * 消费任务，审核文章
     */
    public void scanNewsByTask();
}
