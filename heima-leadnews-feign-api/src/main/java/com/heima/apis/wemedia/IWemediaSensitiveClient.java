package com.heima.apis.wemedia;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-wemedia", contextId = "sensitive")
public interface IWemediaSensitiveClient {
    @PostMapping("/api/v1/sensitive/list")
    ResponseResult listSensitive(@RequestBody SensitiveDto sensitiveDto);

    @PostMapping("/api/v1/sensitive/save")
    ResponseResult saveSensitive(@RequestBody WmSensitive wmSensitive);

    @DeleteMapping("/api/v1/sensitive/del/{id}")
    ResponseResult delSensitive(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/sensitive/update")
    ResponseResult updateSensitive(@RequestBody WmSensitive wmSensitive);
}
