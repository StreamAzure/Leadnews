package com.heima.admin.service;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;

public interface ChannelService {

    ResponseResult delChannel(Integer id);
    ResponseResult listChannel(ChannelDto channelDto);
}
