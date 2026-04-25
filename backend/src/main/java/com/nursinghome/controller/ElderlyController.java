package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.ElderlyQueryDTO;
import com.nursinghome.entity.Elderly;
import com.nursinghome.service.ElderlyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elderly")
public class ElderlyController {

    private final ElderlyService elderlyService;

    public ElderlyController(ElderlyService elderlyService) {
        this.elderlyService = elderlyService;
    }

    @GetMapping("/page")
    public Result<PageResult<Elderly>> page(ElderlyQueryDTO queryDTO) {
        return Result.success(elderlyService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<Elderly> detail(@PathVariable Long id) {
        return Result.success(elderlyService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Elderly elderly) {
        if (elderly.getStatus() == null || elderly.getStatus().isBlank()) {
            elderly.setStatus("在住");
        }
        return Result.success("新增成功", elderlyService.save(elderly));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody Elderly elderly) {
        elderly.setId(id);
        return Result.success("修改成功", elderlyService.updateById(elderly));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", elderlyService.removeById(id));
    }
}
