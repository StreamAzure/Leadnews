package com.heima.admin.controller.v1;

import com.heima.admin.service.UserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class UserLoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdUserDto adUserDto){
        log.info("login...");
        return userService.login(adUserDto);
    }
}
