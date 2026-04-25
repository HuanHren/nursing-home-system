package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.PaymentRecordQueryDTO;
import com.nursinghome.entity.PaymentRecord;
import com.nursinghome.mapper.PaymentRecordMapper;
import com.nursinghome.service.PaymentRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentRecordService {

    @Override
    public PageResult<PaymentRecord> pageList(PaymentRecordQueryDTO queryDTO) {
        Page<PaymentRecord> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<PaymentRecord> wrapper = new LambdaQueryWrapper<PaymentRecord>()
                .like(StringUtils.hasText(queryDTO.getElderlyName()), PaymentRecord::getElderlyName, queryDTO.getElderlyName())
                .eq(StringUtils.hasText(queryDTO.getFeeType()), PaymentRecord::getFeeType, queryDTO.getFeeType())
                .eq(StringUtils.hasText(queryDTO.getPaymentStatus()), PaymentRecord::getPaymentStatus, queryDTO.getPaymentStatus())
                .eq(StringUtils.hasText(queryDTO.getBillingMonth()), PaymentRecord::getBillingMonth, queryDTO.getBillingMonth())
                .orderByDesc(PaymentRecord::getPaymentDate)
                .orderByDesc(PaymentRecord::getCreateTime);

        Page<PaymentRecord> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
