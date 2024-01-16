package com.reno.reno.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderEntity;
import com.reno.reno.model.order.OrderStoreEntity;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.repository.order.OrderStoreRepository;
import com.reno.reno.util.Util;

@Component
public class OrderStoreBusiness {
    private @Autowired OrderStoreRepository orderStoreRepository;
    private @Autowired StoreCodeDiscountBusiness storeCodeDiscountBusiness;

    public OrderStoreEntity saveOrderStore(OrderStoreEntity orderStore) {
        return orderStoreRepository.save(orderStore);
    }

    public OrderStoreEntity convertToNewOrderStore(OrderEntity order, StoreEntity store) {
        OrderStoreEntity orderStore = new OrderStoreEntity();
        orderStore.setOrder(order);
        orderStore.setStore(store);
        orderStore.setUpdatedAt(new Date());
        orderStore.setCreatedAt(new Date());
        return orderStore;
    }

    public OrderStoreEntity shouldSetStoreCodeDiscount(Long storeCodeDiscountId, OrderStoreEntity orderStore)
            throws ApiException {
        if (Util.isNotNull(storeCodeDiscountId)) {
            StoreCodeDiscountEntity storeCodeDiscount = storeCodeDiscountBusiness
                    .shouldGetStoreCodeDiscountByIdOrElseThrows(storeCodeDiscountId);
            orderStore.setStoreCodeDiscount(storeCodeDiscount);
        }
        return orderStore;
    }
}
