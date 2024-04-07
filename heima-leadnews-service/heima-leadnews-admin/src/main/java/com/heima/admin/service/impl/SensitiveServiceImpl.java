package com.heima.admin.service.impl;

import com.heima.admin.service.SensitiveService;
import com.heima.apis.wemedia.IWemdiaSensitiveClient;
import com.heima.apis.wemedia.IWemediaChannelClient;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensitiveServiceImpl implements SensitiveService {
    @Autowired
    private IWemdiaSensitiveClient wemdiaSensitiveClient;
    @Override
    public ResponseResult listSensitive(SensitiveDto sensitiveDto) {
        return wemdiaSensitiveClient.listSensitive(sensitiveDto);
    }
}
