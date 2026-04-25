package com.nursinghome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nursinghome.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {
}
