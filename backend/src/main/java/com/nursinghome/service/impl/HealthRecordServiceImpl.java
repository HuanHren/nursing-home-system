package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.HealthRecordQueryDTO;
import com.nursinghome.entity.HealthRecord;
import com.nursinghome.mapper.HealthRecordMapper;
import com.nursinghome.service.HealthRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {

    @Override
    public PageResult<HealthRecord> pageList(HealthRecordQueryDTO queryDTO) {
        Page<HealthRecord> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<HealthRecord> wrapper = new LambdaQueryWrapper<HealthRecord>()
                .like(StringUtils.hasText(queryDTO.getElderlyName()), HealthRecord::getElderlyName, queryDTO.getElderlyName())
                .eq(StringUtils.hasText(queryDTO.getHealthStatus()), HealthRecord::getHealthStatus, queryDTO.getHealthStatus())
                .eq(queryDTO.getCheckDate() != null, HealthRecord::getCheckDate, queryDTO.getCheckDate())
                .orderByDesc(HealthRecord::getCheckDate)
                .orderByDesc(HealthRecord::getCreateTime);

        Page<HealthRecord> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
