package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.dtos.AdChannel;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Autowired
    private WmChannelMapper wmChannelMapper;

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

    @Override
    public ResponseResult delChannel(Integer id) {
        // 检查参数
        if(id == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        WmChannel wmChannel = getById(id);
        if(wmChannel == null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);

        if(!wmChannel.getStatus()){ // 检查频道状态是否为禁用
            // 删除
            removeById(wmChannel.getId());
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        else{
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
    }

    @Override
    public ResponseResult updateChannel(AdChannel adChannel) {
        log.info("更新频道信息");
        if(adChannel == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        log.info("查询频道：{}", adChannel.getId());
        WmChannel newWmChannel = getById(adChannel.getId());
        if(newWmChannel == null) return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        if(!adChannel.getStatus()) {
            // 禁用频道，需要检查其下是否还有news
            List<WmNews> wmNewsList = wmChannelMapper.selectNewsByChannelId(adChannel.getId());
            log.info("禁用频道，检查频道下是否还有文章");
            if (wmNewsList == null || wmNewsList.isEmpty()) {
                BeanUtils.copyProperties(adChannel, newWmChannel);
                return ResponseResult.okResult(newWmChannel);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_NEWS_EXISTS);
            }
        }
        BeanUtils.copyProperties(adChannel, newWmChannel);
        updateById(newWmChannel);
        log.info("频道更新成功");
        return ResponseResult.okResult(newWmChannel);
    }
}