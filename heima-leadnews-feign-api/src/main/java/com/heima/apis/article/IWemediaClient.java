package com.heima.apis.article;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-wemedia")
public interface IWemediaClient {

    @PostMapping("/api/v1/channel/page")
    public ResponseResult channelPage(@RequestBody ChannelDto channelDto);
}
