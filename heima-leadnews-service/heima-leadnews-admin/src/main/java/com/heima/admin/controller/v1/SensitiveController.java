package com.heima.admin.controller.v1;

import com.heima.admin.service.SensitiveService;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseResult saveSensitive(@RequestBody WmSensitive wmSensitive){
        log.info("新增敏感词: {}",wmSensitive);
        return sensitiveService.saveSensitive(wmSensitive);
    }

    @DeleteMapping("/del/{id}")
    public ResponseResult delSensitive(@PathVariable("id") Integer id){
        log.info("删除敏感词： {}", id);
        return sensitiveService.delSensitive(id);
    }

    @PostMapping("/update")
    public ResponseResult updateSensitive(@RequestBody WmSensitive wmSensitive){
        log.info("修改敏感词： {}", wmSensitive);
        return sensitiveService.updateSensitive(wmSensitive);
    }
}
