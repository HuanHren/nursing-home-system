package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.NursingRecordQueryDTO;
import com.nursinghome.entity.NursingRecord;

public interface NursingRecordService extends IService<NursingRecord> {

    PageResult<NursingRecord> pageList(NursingRecordQueryDTO queryDTO);
}
