package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.SysUserQueryDTO;
import com.nursinghome.entity.SysUser;
import com.nursinghome.service.SysUserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sys-users")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/page")
    public Result<PageResult<SysUser>> page(SysUserQueryDTO queryDTO) {
        return Result.success(sysUserService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<SysUser> detail(@PathVariable Long id) {
        return Result.success(sysUserService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody SysUser user) {
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("正常");
        }
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("STAFF");
        }
        return Result.success("新增成功", sysUserService.save(user));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        return Result.success("修改成功", sysUserService.updateById(user));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", sysUserService.removeById(id));
    }
}
