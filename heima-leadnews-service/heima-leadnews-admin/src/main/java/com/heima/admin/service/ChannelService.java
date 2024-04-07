package com.heima.admin.service;

import com.heima.model.admin.dtos.AdChannel;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;

public interface ChannelService {

    ResponseResult delChannel(Integer id);
    ResponseResult listChannel(ChannelDto channelDto);

    ResponseResult updateChannel(AdChannel adChannel);
}
