package com.reno.reno.model.order.response;

import java.util.List;

import com.reno.reno.model.order.OrderStatusEntity;
import com.reno.reno.model.order.ShippingAddressEntity;
import com.reno.reno.model.payment.PaymentDetailEntity;

import lombok.Data;

@Data
public class ResponseOrderStore {

    OrderStatusEntity orderStatus;

    List<ResponseProductCount> responseProductCount;

    PaymentDetailEntity paymentDetail;

    ShippingAddressEntity shippingAddress;

}
