package com.nursinghome.dto;

import lombok.Data;

@Data
public class StaffInfoQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String name;
    private String position;
    private String department;
    private String status;
}
