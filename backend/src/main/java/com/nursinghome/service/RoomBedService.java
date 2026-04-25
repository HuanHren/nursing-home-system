package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.RoomBedQueryDTO;
import com.nursinghome.entity.RoomBed;

public interface RoomBedService extends IService<RoomBed> {

    PageResult<RoomBed> pageList(RoomBedQueryDTO queryDTO);
}
