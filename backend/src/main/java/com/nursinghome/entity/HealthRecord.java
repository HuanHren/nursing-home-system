package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("health_record")
public class HealthRecord {

    private Long id;
    private Long elderlyId;
    private String elderlyName;
    private String gender;
    private Integer age;
    private String bloodPressure;
    private String bloodSugar;
    private Integer heartRate;
    private BigDecimal weight;
    private String medicalHistory;
    private String allergyHistory;
    private String medication;
    private String healthStatus;
    private LocalDate checkDate;
    private String doctorName;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
