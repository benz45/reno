package com.reno.reno.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.constant.PaymentDetailTypeConstant;
import com.reno.reno.model.payment.PaymentDetailEntity;
import com.reno.reno.model.payment.PaymentDetailTypeEntity;
import com.reno.reno.repository.payment.PaymentDetailRepository;

@Component
public class PaymentDetailBusiness {

    private @Autowired PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailEntity shouldSaveNewPaymentDetail(PaymentDetailTypeEntity paymentDetailType) {
        PaymentDetailEntity paymentDetail = new PaymentDetailEntity();
        paymentDetail.setIsPaidSuccess(paymentDetailType.getId() != PaymentDetailTypeConstant.CASH_ON_DELIVERY);
        paymentDetail.setPaymentDetailType(paymentDetailType);
        paymentDetail.setUpdatedAt(new Date());
        paymentDetail.setCreatedAt(new Date());
        return paymentDetailRepository.save(paymentDetail);
    }
}
