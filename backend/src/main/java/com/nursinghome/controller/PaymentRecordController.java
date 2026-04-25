package com.nursinghome.controller;

import com.nursinghome.common.PageResult;
import com.nursinghome.common.Result;
import com.nursinghome.dto.PaymentRecordQueryDTO;
import com.nursinghome.entity.PaymentRecord;
import com.nursinghome.service.PaymentRecordService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-records")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController(PaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    @GetMapping("/page")
    public Result<PageResult<PaymentRecord>> page(PaymentRecordQueryDTO queryDTO) {
        return Result.success(paymentRecordService.pageList(queryDTO));
    }

    @GetMapping("/{id}")
    public Result<PaymentRecord> detail(@PathVariable Long id) {
        return Result.success(paymentRecordService.getById(id));
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody PaymentRecord paymentRecord) {
        if (paymentRecord.getPaymentStatus() == null || paymentRecord.getPaymentStatus().isBlank()) {
            paymentRecord.setPaymentStatus("未缴费");
        }
        return Result.success("新增成功", paymentRecordService.save(paymentRecord));
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody PaymentRecord paymentRecord) {
        paymentRecord.setId(id);
        return Result.success("修改成功", paymentRecordService.updateById(paymentRecord));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success("删除成功", paymentRecordService.removeById(id));
    }
}
