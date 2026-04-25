package com.nursinghome.dto;

import lombok.Data;

@Data
public class SysUserQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String username;
    private String realName;
    private String role;
    private String status;
}
