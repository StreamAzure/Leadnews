package com.heima.wemedia.feign;

import com.heima.apis.article.IWemediaClient;
import com.heima.model.admin.dtos.AdChannel;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WemediaClient implements IWemediaClient {

    @Autowired
    private WmChannelService wmChannelService;

    @Override
    @PostMapping("/api/v1/channel/page")
    public ResponseResult channelPage(ChannelDto channelDto) {
        log.info("Wemedia 微服务被远程调用：Feign channelPage: {}", channelDto);
        return wmChannelService.channelPage(channelDto);
    }

    @Override
    @GetMapping("/api/v1/channel/del/{id}")
    public ResponseResult delChannel(Integer id) {
        log.info("Wemdia 微服务被远程调用：Feign delChannel: {}", id);
        return wmChannelService.delChannel(id);
    }

    @Override
    @PostMapping("/api/v1/channel/update")
    public ResponseResult updateChannel(AdChannel adChannel) {
        log.info("Wemdia 微服务被远程调用：Feign updateChannel: {}", adChannel);
        return wmChannelService.updateChannel(adChannel);
    }

    @Override
    @PostMapping("/api/v1/channel/save")
    public ResponseResult saveChannel(AdChannel adChannel) {
        log.info("Wemdia 微服务被远程调用：Feign saveChannel: {}", adChannel);
        return wmChannelService.saveChannel(adChannel);
    }
}
