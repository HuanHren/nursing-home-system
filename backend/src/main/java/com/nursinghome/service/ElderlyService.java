package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.ElderlyQueryDTO;
import com.nursinghome.entity.Elderly;

public interface ElderlyService extends IService<Elderly> {

    PageResult<Elderly> pageList(ElderlyQueryDTO queryDTO);
}
