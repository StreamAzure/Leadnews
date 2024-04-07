package com.heima.apis.wemedia;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-wemedia", contextId = "sensitive")
public interface IWemdiaSensitiveClient {
    @PostMapping("/api/v1/sensitive/list")
    ResponseResult listSensitive(@RequestBody SensitiveDto sensitiveDto);
}
