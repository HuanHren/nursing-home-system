package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.HealthRecordQueryDTO;
import com.nursinghome.entity.HealthRecord;
import com.nursinghome.service.HealthRecordService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    public HealthRecordController(HealthRecordService healthRecordService) {
        this.healthRecordService = healthRecordService;
    }

    @GetMapping("/page")
    public Result<PageResult<HealthRecord>> page(HealthRecordQueryDTO queryDTO) {
        return Result.success(healthRecordService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<HealthRecord> detail(@PathVariable Long id) {
        return Result.success(healthRecordService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody HealthRecord healthRecord) {
        if (healthRecord.getHealthStatus() == null || healthRecord.getHealthStatus().isBlank()) {
            healthRecord.setHealthStatus("良好");
        }
        return Result.success("新增成功", healthRecordService.save(healthRecord));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody HealthRecord healthRecord) {
        healthRecord.setId(id);
        return Result.success("修改成功", healthRecordService.updateById(healthRecord));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", healthRecordService.removeById(id));
    }
}
