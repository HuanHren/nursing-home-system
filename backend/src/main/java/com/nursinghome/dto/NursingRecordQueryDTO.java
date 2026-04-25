package com.nursinghome.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NursingRecordQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String elderlyName;
    private String staffName;
    private String nursingType;
    private LocalDate nursingDate;
    private String status;
}
