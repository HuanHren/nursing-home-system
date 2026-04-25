package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.NoticeQueryDTO;
import com.nursinghome.entity.Notice;
import com.nursinghome.service.NoticeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/page")
    public Result<PageResult<Notice>> page(NoticeQueryDTO queryDTO) {
        return Result.success(noticeService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<Notice> detail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Notice notice) {
        if (notice.getPublishStatus() == null || notice.getPublishStatus().isBlank()) {
            notice.setPublishStatus("草稿");
        }
        if (notice.getSortOrder() == null) {
            notice.setSortOrder(0);
        }
        return Result.success("新增成功", noticeService.save(notice));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        return Result.success("修改成功", noticeService.updateById(notice));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", noticeService.removeById(id));
    }
}
