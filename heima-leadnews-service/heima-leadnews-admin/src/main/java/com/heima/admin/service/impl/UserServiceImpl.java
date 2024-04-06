package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.UserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.utils.common.AppJwtUtil;
import org.apache.commons.lang.StringUtils;
import org.jcodings.util.Hash;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

import static com.heima.model.common.enums.AppHttpCodeEnum.*;

@Service
public class UserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements UserService {

    @Override
    public ResponseResult login(AdUserDto adUserDto) {
        if(StringUtils.isNotBlank(adUserDto.getName()) && StringUtils.isNotBlank(adUserDto.getPassword())){
            AdUser adUser = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, adUserDto.getName()));
            if(adUser == null){
                return ResponseResult.errorResult(AD_USER_NAME_NOT_EXIST, AD_USER_NAME_NOT_EXIST.getErrorMessage());
            }
            String salt = adUser.getSalt();
            String password = adUserDto.getPassword();
            password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            // 将前端登录密码加盐处理后，与数据库中的密码比对
            if(!password.equals(adUser.getPassword())){
                return ResponseResult.errorResult(LOGIN_PASSWORD_ERROR, LOGIN_PASSWORD_ERROR.getErrorMessage());
            }

            // 删掉密码和盐后把用户数据返回给前端
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(adUser.getId().longValue()));
            adUser.setSalt("");
            adUser.setPassword("");
            map.put("user", adUser);
            return ResponseResult.okResult(map);
        }
        else{
            return ResponseResult.errorResult(USERNAME_PASSWORD_REQUIRE, USERNAME_PASSWORD_REQUIRE.getErrorMessage());
        }
    }
}
