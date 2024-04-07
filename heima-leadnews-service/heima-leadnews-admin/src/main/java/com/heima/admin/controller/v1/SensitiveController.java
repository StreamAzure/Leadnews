package com.heima.admin.controller.v1;

import com.heima.admin.service.SensitiveService;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensitive")
@Slf4j
public class SensitiveController {
    @Autowired
    private SensitiveService sensitiveService;

    @PostMapping("/list")
    public ResponseResult listSensitive(@RequestBody SensitiveDto sensitiveDto){
        log.info("敏感词分页查询");
        return sensitiveService.listSensitive(sensitiveDto);
    }
}
