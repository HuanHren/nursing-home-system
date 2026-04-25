package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.LoginDTO;
import com.nursinghome.dto.RegisterDTO;
import com.nursinghome.dto.SysUserQueryDTO;
import com.nursinghome.entity.SysUser;
import com.nursinghome.vo.LoginVO;

public interface SysUserService extends IService<SysUser> {

    LoginVO login(LoginDTO loginDTO);

    Boolean register(RegisterDTO registerDTO);

    PageResult<SysUser> pageList(SysUserQueryDTO queryDTO);
}
