package com.heima.apis.article;

import com.heima.model.admin.dtos.AdChannel;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-wemedia")
public interface IWemediaClient {

    @PostMapping("/api/v1/channel/page")
    public ResponseResult channelPage(@RequestBody ChannelDto channelDto);

    @GetMapping("/api/v1/channel/del/{id}")
    public ResponseResult delChannel(@PathVariable("id") Integer id);

    @PostMapping("/api/v1/channel/update")
    ResponseResult updateChannel(@RequestBody AdChannel adChannel);
}
