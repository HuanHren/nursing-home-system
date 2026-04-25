package com.nursinghome.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HealthRecordQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String elderlyName;
    private String healthStatus;
    private LocalDate checkDate;
}
