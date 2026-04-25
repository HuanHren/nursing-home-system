package com.nursinghome.controller;

import com.nursinghome.common.Result;
import com.nursinghome.service.DashboardService;
import com.nursinghome.vo.DashboardSummaryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public Result<DashboardSummaryVO> summary() {
        return Result.success(dashboardService.getSummary());
    }
}
