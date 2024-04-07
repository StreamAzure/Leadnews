package com.heima.wemedia.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.apis.wemedia.IWemediaSensitiveClient;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sensitive")
@Slf4j
public class WemediaSensitiveClient implements IWemediaSensitiveClient {

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;
    @Override
    @PostMapping("/list")
    public ResponseResult listSensitive(SensitiveDto sensitiveDto) {
        log.info("Wemedia 微服务被远程调用：Feign listSensitive: {}", sensitiveDto);
        if(sensitiveDto == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        //分页参数
        Page<WmSensitive> rawPage = new Page(sensitiveDto.getPage(), sensitiveDto.getSize());
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        rawPage = wmSensitiveMapper.selectPage(rawPage, queryWrapper);
        return ResponseResult.okResult(rawPage.getRecords());
    }

    @Override
    @PostMapping("/save")
    public ResponseResult saveSensitive(WmSensitive wmSensitive) {
        log.info("Wemedia 微服务被远程调用：Feign saveSensitive: {}", wmSensitive);
        if(wmSensitive == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        // 检查敏感词是否已存在
        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件，这里以敏感词word字段为例
        queryWrapper.eq(WmSensitive::getSensitives, wmSensitive.getSensitives());
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(queryWrapper);
        if(wmSensitives == null || wmSensitives.isEmpty()){
            wmSensitive.setCreatedTime(new Date());
            wmSensitiveMapper.insert(wmSensitive);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delSensitive(Integer id) {
        log.info("Wemedia 微服务被远程调用：Feign delSensitive: {}", id);
        if(id == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        wmSensitiveMapper.deleteById(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @PostMapping("/update")
    public ResponseResult updateSensitive(WmSensitive wmSensitive) {
        log.info("Wemedia 微服务被远程调用：Feign updateSensitive: {}", wmSensitive);
        if(wmSensitive == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        wmSensitiveMapper.updateById(wmSensitive);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
