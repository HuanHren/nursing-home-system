package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.NoticeQueryDTO;
import com.nursinghome.entity.Notice;
import com.nursinghome.mapper.NoticeMapper;
import com.nursinghome.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public PageResult<Notice> pageList(NoticeQueryDTO queryDTO) {
        Page<Notice> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<Notice>()
                .like(StringUtils.hasText(queryDTO.getTitle()), Notice::getTitle, queryDTO.getTitle())
                .eq(StringUtils.hasText(queryDTO.getNoticeType()), Notice::getNoticeType, queryDTO.getNoticeType())
                .eq(StringUtils.hasText(queryDTO.getPublishStatus()), Notice::getPublishStatus, queryDTO.getPublishStatus())
                .orderByDesc(Notice::getSortOrder)
                .orderByDesc(Notice::getPublishTime)
                .orderByDesc(Notice::getCreateTime);

        Page<Notice> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
