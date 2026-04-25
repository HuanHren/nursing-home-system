package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.NursingRecordQueryDTO;
import com.nursinghome.entity.NursingRecord;
import com.nursinghome.service.NursingRecordService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nursing-records")
public class NursingRecordController {

    private final NursingRecordService nursingRecordService;

    public NursingRecordController(NursingRecordService nursingRecordService) {
        this.nursingRecordService = nursingRecordService;
    }

    @GetMapping("/page")
    public Result<PageResult<NursingRecord>> page(NursingRecordQueryDTO queryDTO) {
        return Result.success(nursingRecordService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<NursingRecord> detail(@PathVariable Long id) {
        return Result.success(nursingRecordService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody NursingRecord nursingRecord) {
        if (nursingRecord.getStatus() == null || nursingRecord.getStatus().isBlank()) {
            nursingRecord.setStatus("正常");
        }
        return Result.success("新增成功", nursingRecordService.save(nursingRecord));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody NursingRecord nursingRecord) {
        nursingRecord.setId(id);
        return Result.success("修改成功", nursingRecordService.updateById(nursingRecord));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", nursingRecordService.removeById(id));
    }
}
