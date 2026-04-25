package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.PaymentRecordQueryDTO;
import com.nursinghome.entity.PaymentRecord;

public interface PaymentRecordService extends IService<PaymentRecord> {

    PageResult<PaymentRecord> pageList(PaymentRecordQueryDTO queryDTO);
}
