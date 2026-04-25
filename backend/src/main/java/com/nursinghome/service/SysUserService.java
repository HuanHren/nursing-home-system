package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.dto.LoginDTO;
import com.nursinghome.entity.SysUser;
import com.nursinghome.vo.LoginVO;

public interface SysUserService extends IService<SysUser> {

    LoginVO login(LoginDTO loginDTO);
}
