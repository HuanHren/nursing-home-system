package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.RoomBedQueryDTO;
import com.nursinghome.entity.RoomBed;
import com.nursinghome.service.RoomBedService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room-beds")
public class RoomBedController {

    private final RoomBedService roomBedService;

    public RoomBedController(RoomBedService roomBedService) {
        this.roomBedService = roomBedService;
    }

    @GetMapping("/page")
    public Result<PageResult<RoomBed>> page(RoomBedQueryDTO queryDTO) {
        return Result.success(roomBedService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<RoomBed> detail(@PathVariable Long id) {
        return Result.success(roomBedService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody RoomBed roomBed) {
        if (roomBed.getStatus() == null || roomBed.getStatus().isBlank()) {
            roomBed.setStatus("空闲");
        }
        return Result.success("新增成功", roomBedService.save(roomBed));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody RoomBed roomBed) {
        roomBed.setId(id);
        return Result.success("修改成功", roomBedService.updateById(roomBed));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", roomBedService.removeById(id));
    }
}
