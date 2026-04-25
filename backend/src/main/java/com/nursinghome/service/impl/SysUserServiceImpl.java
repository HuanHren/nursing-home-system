package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.dto.LoginDTO;
import com.nursinghome.entity.SysUser;
import com.nursinghome.mapper.SysUserMapper;
import com.nursinghome.service.SysUserService;
import com.nursinghome.vo.LoginVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        SysUser user = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, loginDTO.getUsername())
                .last("limit 1"));

        if (user == null || !StringUtils.hasText(user.getPassword())
                || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
        return new LoginVO(token, user.getId(), user.getUsername(), user.getRealName(), user.getRole());
    }
}
