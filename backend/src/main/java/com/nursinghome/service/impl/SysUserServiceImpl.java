package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.LoginDTO;
import com.nursinghome.dto.RegisterDTO;
import com.nursinghome.dto.SysUserQueryDTO;
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
        if ("禁用".equals(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }

        String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
        return new LoginVO(token, user.getId(), user.getUsername(), user.getRealName(), user.getRole(), user.getElderlyId());
    }

    @Override
    public Boolean register(RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        Long count = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, registerDTO.getUsername()));
        if (count != null && count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setRealName(registerDTO.getRealName());
        user.setPhone(registerDTO.getPhone());
        user.setRole("FAMILY");
        user.setStatus("正常");
        return save(user);
    }

    @Override
    public PageResult<SysUser> pageList(SysUserQueryDTO queryDTO) {
        Page<SysUser> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.hasText(queryDTO.getUsername()), SysUser::getUsername, queryDTO.getUsername())
                .like(StringUtils.hasText(queryDTO.getRealName()), SysUser::getRealName, queryDTO.getRealName())
                .eq(StringUtils.hasText(queryDTO.getRole()), SysUser::getRole, queryDTO.getRole())
                .eq(StringUtils.hasText(queryDTO.getStatus()), SysUser::getStatus, queryDTO.getStatus())
                .orderByDesc(SysUser::getCreateTime);
        Page<SysUser> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
