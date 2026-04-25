package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.StaffInfoQueryDTO;
import com.nursinghome.entity.StaffInfo;
import com.nursinghome.service.StaffInfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff-info")
public class StaffInfoController {

    private final StaffInfoService staffInfoService;

    public StaffInfoController(StaffInfoService staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @GetMapping("/page")
    public Result<PageResult<StaffInfo>> page(StaffInfoQueryDTO queryDTO) {
        return Result.success(staffInfoService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<StaffInfo> detail(@PathVariable Long id) {
        return Result.success(staffInfoService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody StaffInfo staffInfo) {
        if (staffInfo.getStatus() == null || staffInfo.getStatus().isBlank()) {
            staffInfo.setStatus("在职");
        }
        return Result.success("新增成功", staffInfoService.save(staffInfo));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody StaffInfo staffInfo) {
        staffInfo.setId(id);
        return Result.success("修改成功", staffInfoService.updateById(staffInfo));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", staffInfoService.removeById(id));
    }
}
