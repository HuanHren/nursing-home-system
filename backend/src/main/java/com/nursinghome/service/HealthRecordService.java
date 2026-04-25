package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.HealthRecordQueryDTO;
import com.nursinghome.entity.HealthRecord;

public interface HealthRecordService extends IService<HealthRecord> {

    PageResult<HealthRecord> pageList(HealthRecordQueryDTO queryDTO);
}
