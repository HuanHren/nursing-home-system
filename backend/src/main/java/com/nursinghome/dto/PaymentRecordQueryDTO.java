package com.nursinghome.dto;

import lombok.Data;

@Data
public class PaymentRecordQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String elderlyName;
    private String feeType;
    private String paymentStatus;
    private String billingMonth;
}
