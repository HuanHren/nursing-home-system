package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.NursingRecordQueryDTO;
import com.nursinghome.entity.NursingRecord;
import com.nursinghome.mapper.NursingRecordMapper;
import com.nursinghome.service.NursingRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NursingRecordServiceImpl extends ServiceImpl<NursingRecordMapper, NursingRecord> implements NursingRecordService {

    @Override
    public PageResult<NursingRecord> pageList(NursingRecordQueryDTO queryDTO) {
        Page<NursingRecord> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<NursingRecord> wrapper = new LambdaQueryWrapper<NursingRecord>()
                .like(StringUtils.hasText(queryDTO.getElderlyName()), NursingRecord::getElderlyName, queryDTO.getElderlyName())
                .like(StringUtils.hasText(queryDTO.getStaffName()), NursingRecord::getStaffName, queryDTO.getStaffName())
                .eq(StringUtils.hasText(queryDTO.getNursingType()), NursingRecord::getNursingType, queryDTO.getNursingType())
                .eq(queryDTO.getNursingDate() != null, NursingRecord::getNursingDate, queryDTO.getNursingDate())
                .eq(StringUtils.hasText(queryDTO.getStatus()), NursingRecord::getStatus, queryDTO.getStatus())
                .orderByDesc(NursingRecord::getNursingDate)
                .orderByDesc(NursingRecord::getCreateTime);

        Page<NursingRecord> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
