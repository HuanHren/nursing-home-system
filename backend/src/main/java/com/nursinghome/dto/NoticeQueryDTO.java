package com.nursinghome.dto;

import lombok.Data;

@Data
public class NoticeQueryDTO {

    private Long current = 1L;
    private Long size = 10L;
    private String title;
    private String noticeType;
    private String publishStatus;
}
