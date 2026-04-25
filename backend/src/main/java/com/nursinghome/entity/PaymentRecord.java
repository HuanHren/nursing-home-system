package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("payment_record")
public class PaymentRecord {

    private Long id;
    private Long elderlyId;
    private String elderlyName;
    private String feeType;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String billingMonth;
    private String operatorName;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
