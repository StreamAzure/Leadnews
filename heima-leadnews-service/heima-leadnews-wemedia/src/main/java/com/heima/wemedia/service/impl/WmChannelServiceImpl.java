package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {


    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    public ResponseResult channelPage(ChannelDto channelDto){
        //分页参数
        Page<WmChannel> rawPage = new Page(channelDto.getPage(), channelDto.getSize());

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
        rawPage = this.baseMapper.selectPage(rawPage, queryWrapper);
        return ResponseResult.okResult(rawPage.getRecords());
    }
}