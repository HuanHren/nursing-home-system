package com.nursinghome.controller;

import com.nursinghome.common.Result;
import com.nursinghome.dto.LoginDTO;
import com.nursinghome.dto.RegisterDTO;
import com.nursinghome.service.SysUserService;
import com.nursinghome.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SysUserService sysUserService;

    public AuthController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.success("登录成功", sysUserService.login(loginDTO));
    }

    @PostMapping("/register")
    public Result<Boolean> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return Result.success("注册成功", sysUserService.register(registerDTO));
    }
}
