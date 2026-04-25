package com.nursinghome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursinghome.entity.Elderly;
import com.nursinghome.entity.HealthRecord;
import com.nursinghome.entity.Notice;
import com.nursinghome.entity.NursingRecord;
import com.nursinghome.entity.PaymentRecord;
import com.nursinghome.entity.RoomBed;
import com.nursinghome.entity.StaffInfo;
import com.nursinghome.mapper.ElderlyMapper;
import com.nursinghome.mapper.HealthRecordMapper;
import com.nursinghome.mapper.NoticeMapper;
import com.nursinghome.mapper.NursingRecordMapper;
import com.nursinghome.mapper.PaymentRecordMapper;
import com.nursinghome.mapper.RoomBedMapper;
import com.nursinghome.mapper.StaffInfoMapper;
import com.nursinghome.service.DashboardService;
import com.nursinghome.vo.DashboardSummaryVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ElderlyMapper elderlyMapper;
    private final StaffInfoMapper staffInfoMapper;
    private final RoomBedMapper roomBedMapper;
    private final HealthRecordMapper healthRecordMapper;
    private final NursingRecordMapper nursingRecordMapper;
    private final PaymentRecordMapper paymentRecordMapper;
    private final NoticeMapper noticeMapper;

    public DashboardServiceImpl(ElderlyMapper elderlyMapper,
                                StaffInfoMapper staffInfoMapper,
                                RoomBedMapper roomBedMapper,
                                HealthRecordMapper healthRecordMapper,
                                NursingRecordMapper nursingRecordMapper,
                                PaymentRecordMapper paymentRecordMapper,
                                NoticeMapper noticeMapper) {
        this.elderlyMapper = elderlyMapper;
        this.staffInfoMapper = staffInfoMapper;
        this.roomBedMapper = roomBedMapper;
        this.healthRecordMapper = healthRecordMapper;
        this.nursingRecordMapper = nursingRecordMapper;
        this.paymentRecordMapper = paymentRecordMapper;
        this.noticeMapper = noticeMapper;
    }

    @Override
    public DashboardSummaryVO getSummary() {
        DashboardSummaryVO summary = new DashboardSummaryVO();

        summary.setElderlyCount(elderlyMapper.selectCount(null));
        summary.setStaffCount(staffInfoMapper.selectCount(null));
        summary.setTotalBedCount(roomBedMapper.selectCount(null));
        summary.setOccupiedBedCount(roomBedMapper.selectCount(new LambdaQueryWrapper<RoomBed>()
                .eq(RoomBed::getStatus, "已入住")));
        summary.setFreeBedCount(roomBedMapper.selectCount(new LambdaQueryWrapper<RoomBed>()
                .eq(RoomBed::getStatus, "空闲")));
        summary.setHealthAttentionCount(healthRecordMapper.selectCount(new LambdaQueryWrapper<HealthRecord>()
                .eq(HealthRecord::getHealthStatus, "需重点关注")));
        summary.setUnpaidCount(paymentRecordMapper.selectCount(new LambdaQueryWrapper<PaymentRecord>()
                .eq(PaymentRecord::getPaymentStatus, "未缴费")));
        summary.setNoticeCount(noticeMapper.selectCount(null));
        summary.setTodayNursingCount(nursingRecordMapper.selectCount(new LambdaQueryWrapper<NursingRecord>()
                .eq(NursingRecord::getNursingDate, LocalDate.now())));
        summary.setRecentNotices(noticeMapper.selectPage(new Page<>(1, 5), new LambdaQueryWrapper<Notice>()
                .eq(Notice::getPublishStatus, "已发布")
                .orderByDesc(Notice::getSortOrder)
                .orderByDesc(Notice::getPublishTime)
                .orderByDesc(Notice::getCreateTime)).getRecords());

        return summary;
    }
}
