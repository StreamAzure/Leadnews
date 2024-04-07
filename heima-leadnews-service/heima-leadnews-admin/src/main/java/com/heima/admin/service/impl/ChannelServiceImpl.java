package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.admin.service.ChannelService;
import com.heima.apis.article.IWemediaClient;
import com.heima.model.admin.dtos.AdChannel;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private IWemediaClient wemediaClient;
    @Override
    public ResponseResult delChannel(Integer id) {
        log.info("删除频道：{}",id);
        return wemediaClient.delChannel(id);
    }

    @Override
    public ResponseResult listChannel(ChannelDto channelDto) {
        log.info("分页查询频道");
        return wemediaClient.channelPage(channelDto);
    }

    @Override
    public ResponseResult updateChannel(AdChannel adChannel) {
        log.info("修改频道");
        return wemediaClient.updateChannel(adChannel);
    }

    @Override
    public ResponseResult saveChannel(AdChannel adChannel) {
        return wemediaClient.saveChannel(adChannel);
    }
}
