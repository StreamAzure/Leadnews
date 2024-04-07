package com.heima.admin.service.impl;

import com.heima.admin.service.SensitiveService;
import com.heima.apis.wemedia.IWemediaSensitiveClient;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensitiveServiceImpl implements SensitiveService {
    @Autowired
    private IWemediaSensitiveClient wemediaSensitiveClient;
    @Override
    public ResponseResult listSensitive(SensitiveDto sensitiveDto) {
        return wemediaSensitiveClient.listSensitive(sensitiveDto);
    }

    @Override
    public ResponseResult saveSensitive(WmSensitive wmSensitive) {
        return wemediaSensitiveClient.saveSensitive(wmSensitive);
    }

    @Override
    public ResponseResult delSensitive(Integer id) {
        return wemediaSensitiveClient.delSensitive(id);
    }

    @Override
    public ResponseResult updateSensitive(WmSensitive wmSensitive) {
        return wemediaSensitiveClient.updateSensitive(wmSensitive);
    }
}
