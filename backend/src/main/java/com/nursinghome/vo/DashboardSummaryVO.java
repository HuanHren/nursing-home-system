package com.nursinghome.vo;

import com.nursinghome.entity.Notice;
import lombok.Data;

import java.util.List;

@Data
public class DashboardSummaryVO {

    private Long elderlyCount;
    private Long staffCount;
    private Long totalBedCount;
    private Long occupiedBedCount;
    private Long freeBedCount;
    private Long healthAttentionCount;
    private Long unpaidCount;
    private Long noticeCount;
    private Long todayNursingCount;
    private List<Notice> recentNotices;
}
