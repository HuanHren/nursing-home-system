package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.StaffInfoQueryDTO;
import com.nursinghome.entity.StaffInfo;

public interface StaffInfoService extends IService<StaffInfo> {

    PageResult<StaffInfo> pageList(StaffInfoQueryDTO queryDTO);
}
