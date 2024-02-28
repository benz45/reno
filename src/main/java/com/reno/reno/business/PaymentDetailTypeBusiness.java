package com.reno.reno.business;

import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.payment.PaymentDetailTypeEntity;
import com.reno.reno.repository.payment.PaymentDetailTypeRepository;
import com.reno.reno.util.Util;

@Component
public class PaymentDetailTypeBusiness {
    private @Autowired PaymentDetailTypeRepository paymentDetailTypeRepository;

    public PaymentDetailTypeEntity shouldGetPaymentDetailTypeByIdOrElseThrow(@NotNull Integer paymentDetailTypeId)
            throws ApiException {
        if (!Util.isNotNull(paymentDetailTypeId)) {
            throw new ApiException("400", "Product detail type id is null.");
        }
        return paymentDetailTypeRepository.findById(paymentDetailTypeId).orElseThrow(
                () -> new ApiException("400", "Can't find product detail type id " +
                        paymentDetailTypeId.toString()));
    }

}
