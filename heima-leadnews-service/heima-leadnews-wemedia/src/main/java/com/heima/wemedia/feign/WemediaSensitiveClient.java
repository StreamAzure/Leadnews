package com.heima.wemedia.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.apis.wemedia.IWemdiaSensitiveClient;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensitive")
@Slf4j
public class WemediaSensitiveClient implements IWemdiaSensitiveClient {

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;
    @Override
    @PostMapping("/list")
    public ResponseResult listSensitive(SensitiveDto sensitiveDto) {
        log.info("Wemdia 微服务被远程调用：Feign listSensitive: {}", sensitiveDto);
        if(sensitiveDto == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        //分页参数
        Page<WmSensitive> rawPage = new Page(sensitiveDto.getPage(), sensitiveDto.getSize());
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        rawPage = wmSensitiveMapper.selectPage(rawPage, queryWrapper);
        return ResponseResult.okResult(rawPage.getRecords());
    }
}
