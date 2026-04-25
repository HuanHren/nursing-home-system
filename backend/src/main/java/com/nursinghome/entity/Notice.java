package com.nursinghome.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("notice")
public class Notice {

    private Long id;
    private String title;
    private String content;
    private String noticeType;
    private String publishStatus;
    private String publisher;
    private LocalDateTime publishTime;
    private Integer sortOrder;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
