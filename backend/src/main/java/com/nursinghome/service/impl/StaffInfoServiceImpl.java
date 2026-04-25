package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.StaffInfoQueryDTO;
import com.nursinghome.entity.StaffInfo;
import com.nursinghome.mapper.StaffInfoMapper;
import com.nursinghome.service.StaffInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements StaffInfoService {

    @Override
    public PageResult<StaffInfo> pageList(StaffInfoQueryDTO queryDTO) {
        Page<StaffInfo> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<StaffInfo> wrapper = new LambdaQueryWrapper<StaffInfo>()
                .like(StringUtils.hasText(queryDTO.getName()), StaffInfo::getName, queryDTO.getName())
                .like(StringUtils.hasText(queryDTO.getPosition()), StaffInfo::getPosition, queryDTO.getPosition())
                .like(StringUtils.hasText(queryDTO.getDepartment()), StaffInfo::getDepartment, queryDTO.getDepartment())
                .eq(StringUtils.hasText(queryDTO.getStatus()), StaffInfo::getStatus, queryDTO.getStatus())
                .orderByDesc(StaffInfo::getCreateTime);

        Page<StaffInfo> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
