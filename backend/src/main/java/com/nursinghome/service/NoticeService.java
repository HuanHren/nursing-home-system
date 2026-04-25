package com.nursinghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.NoticeQueryDTO;
import com.nursinghome.entity.Notice;

public interface NoticeService extends IService<Notice> {

    PageResult<Notice> pageList(NoticeQueryDTO queryDTO);
}
