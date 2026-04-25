package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.RoomBedQueryDTO;
import com.nursinghome.entity.RoomBed;
import com.nursinghome.mapper.RoomBedMapper;
import com.nursinghome.service.RoomBedService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomBedServiceImpl extends ServiceImpl<RoomBedMapper, RoomBed> implements RoomBedService {

    @Override
    public PageResult<RoomBed> pageList(RoomBedQueryDTO queryDTO) {
        Page<RoomBed> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<RoomBed> wrapper = new LambdaQueryWrapper<RoomBed>()
                .like(StringUtils.hasText(queryDTO.getBuildingNo()), RoomBed::getBuildingNo, queryDTO.getBuildingNo())
                .like(StringUtils.hasText(queryDTO.getRoomNo()), RoomBed::getRoomNo, queryDTO.getRoomNo())
                .like(StringUtils.hasText(queryDTO.getBedNo()), RoomBed::getBedNo, queryDTO.getBedNo())
                .eq(StringUtils.hasText(queryDTO.getStatus()), RoomBed::getStatus, queryDTO.getStatus())
                .orderByDesc(RoomBed::getCreateTime);

        Page<RoomBed> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
