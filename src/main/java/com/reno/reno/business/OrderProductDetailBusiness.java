package com.reno.reno.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.order.OrderProductDetailEntity;
import com.reno.reno.model.order.OrderStoreEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.repository.order.OrderProductDetailRepository;

@Component
public class OrderProductDetailBusiness {
    private @Autowired OrderProductDetailRepository orderProductDetailRepository;

    public OrderProductDetailEntity saveOrderProductDetail(OrderProductDetailEntity orderProductDetail) {
        return orderProductDetailRepository.save(orderProductDetail);
    }

    public List<OrderProductDetailEntity> saveAllOrderProductDetail(
            List<OrderProductDetailEntity> orderProductDetails) {
        return orderProductDetailRepository.saveAll(orderProductDetails);
    }

    public OrderProductDetailEntity convertNewOrderProductDetail(ProductEntity product, Integer amount,
            OrderStoreEntity orderStore) {
        OrderProductDetailEntity orderProductDetail = new OrderProductDetailEntity();
        orderProductDetail.setProduct(product);
        orderProductDetail.setPrice(product.getPrice());
        orderProductDetail.setAmount(amount);
        orderProductDetail.setCreatedAt(new Date());
        orderProductDetail.setOrderStore(orderStore);
        return orderProductDetail;
    }
}
