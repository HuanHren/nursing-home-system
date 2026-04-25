package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("staff_info")
public class StaffInfo {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String position;
    private String department;
    private LocalDate hireDate;
    private String status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
