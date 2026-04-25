package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nursinghome.common.PageResult;
import com.nursinghome.dto.ElderlyQueryDTO;
import com.nursinghome.entity.Elderly;
import com.nursinghome.mapper.ElderlyMapper;
import com.nursinghome.service.ElderlyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ElderlyServiceImpl extends ServiceImpl<ElderlyMapper, Elderly> implements ElderlyService {

    @Override
    public PageResult<Elderly> pageList(ElderlyQueryDTO queryDTO) {
        Page<Elderly> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        LambdaQueryWrapper<Elderly> wrapper = new LambdaQueryWrapper<Elderly>()
                .like(StringUtils.hasText(queryDTO.getName()), Elderly::getName, queryDTO.getName())
                .eq(StringUtils.hasText(queryDTO.getStatus()), Elderly::getStatus, queryDTO.getStatus())
                .orderByDesc(Elderly::getCreateTime);

        Page<Elderly> result = page(page, wrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }
}
