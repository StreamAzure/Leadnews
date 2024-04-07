package com.heima.admin.service;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmSensitive;

public interface SensitiveService {
    ResponseResult listSensitive(SensitiveDto sensitiveDto);

    ResponseResult saveSensitive(WmSensitive wmSensitive);

    ResponseResult delSensitive(Integer id);

    ResponseResult updateSensitive(WmSensitive wmSensitive);
}
