package com.heima.admin.service;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;

public interface UserService {
    public ResponseResult login(AdUserDto adUserDto);
}
