package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("elderly")
public class Elderly {

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String idCard;
    private String phone;
    private String familyPhone;
    private String address;
    private LocalDate checkInDate;
    private String status;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
