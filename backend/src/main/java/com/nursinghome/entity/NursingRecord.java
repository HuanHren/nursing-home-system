package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("nursing_record")
public class NursingRecord {

    private Long id;
    private Long elderlyId;
    private String elderlyName;
    private Long staffId;
    private String staffName;
    private String nursingType;
    private String nursingContent;
    private String nursingResult;
    private LocalDate nursingDate;
    private LocalTime nursingTime;
    private String status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
