package com.heima.admin.controller.v1;

import com.heima.admin.service.ChannelService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
@Slf4j
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping("/del/{id}")
    public ResponseResult delChannel(@PathVariable("id") Integer id){
        return channelService.delChannel(id);
    }

    @PostMapping("/list")
    public ResponseResult listChannel(@RequestBody ChannelDto channelDto){
        log.info("频道分页");
        return channelService.listChannel(channelDto);
    }
}