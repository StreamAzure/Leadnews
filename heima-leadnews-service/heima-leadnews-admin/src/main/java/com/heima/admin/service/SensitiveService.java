package com.heima.admin.service;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.common.dtos.ResponseResult;

public interface SensitiveService {
    ResponseResult listSensitive(SensitiveDto sensitiveDto);
}
