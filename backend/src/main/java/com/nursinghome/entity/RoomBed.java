package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("room_bed")
public class RoomBed {

    private Long id;
    private String buildingNo;
    private String roomNo;
    private String bedNo;
    private String bedType;
    private String status;
    private Long elderlyId;
    private String elderlyName;
    private BigDecimal price;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
