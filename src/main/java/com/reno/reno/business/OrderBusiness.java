package com.reno.reno.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderEntity;
import com.reno.reno.model.order.OrderProductDetailEntity;
import com.reno.reno.model.order.OrderStoreEntity;
import com.reno.reno.model.order.request.RequestCreateOrder;
import com.reno.reno.model.order.request.RequestOrder;
import com.reno.reno.model.order.request.RequestProduct;
import com.reno.reno.model.payment.PaymentDetailEntity;
import com.reno.reno.model.payment.PaymentDetailTypeEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.repository.order.OrderRepository;
import com.reno.reno.util.Util;

@Component
public class OrderBusiness {

    private @Autowired OrderRepository orderRepository;
    private @Autowired CustomerBusiness customerBusiness;
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired PaymentDetailBusiness paymentDetailBusiness;
    private @Autowired PaymentDetailTypeBusiness paymentDetailTypeBusiness;
    private @Autowired OrderStoreBusiness orderStoreBusiness;
    private @Autowired OrderProductDetailBusiness orderProductDetailBusiness;
    private @Autowired ProductBusiness productBusiness;
    private @Autowired StoreCodeDiscountBusiness storeCodeDiscountBusiness;

    @Transactional(rollbackFor = Exception.class)
    public OrderEntity createOrder(RequestCreateOrder requestCreateOrder) throws ApiException {
        CustomerEntity customer = customerBusiness.getCustomerById(requestCreateOrder.getCustomerId());
        OrderEntity order = new OrderEntity();
        order.setCreatedBy(customer.getId());
        PaymentDetailTypeEntity paymentDetailType = paymentDetailTypeBusiness.shouldGetPaymentDetailTypeByIdOrElseThrow(
                requestCreateOrder.getPaymentDetailTypeId());
        PaymentDetailEntity paymentDetail = paymentDetailBusiness.shouldSaveNewPaymentDetail(paymentDetailType);
        order.setPaymentDetail(paymentDetail);
        for (RequestOrder _order : requestCreateOrder.getOrders()) {
            StoreEntity store = storeBusiness.shouldGetStoreByIdOrElseThrow(_order.getStoreId());
            OrderStoreEntity orderStore = orderStoreBusiness.convertToNewOrderStore(order, store);
            orderStore = orderStoreBusiness.shouldSetOrderStatus(paymentDetailType, orderStore);
            orderStore = orderStoreBusiness.shouldSetStoreCodeDiscount(_order.getStoreCodeDiscountId(), orderStore);
            orderStore = orderStoreBusiness.saveOrderStore(orderStore);

            List<OrderProductDetailEntity> orderProductDetails = new ArrayList<>();
            for (RequestProduct _product : _order.getProducts()) {
                productBusiness.shouldSetProductAmountWithOrderAmount(_product.getProductId(), _product.getAmount());
                ProductEntity product = productBusiness.shouldGetProductByIdOrElseThrow(_product.getProductId());
                OrderProductDetailEntity orderProductDetail = orderProductDetailBusiness
                        .convertNewOrderProductDetail(product, _product.getAmount(), orderStore);
                orderProductDetails.add(orderProductDetail);
            }

            if (Util.isNotNull(_order.getStoreCodeDiscountId())) {
                StoreCodeDiscountEntity storeCodeDiscount = storeCodeDiscountBusiness
                        .shouldGetStoreCodeDiscountByIdOrElseThrows(_order.getStoreCodeDiscountId());
                orderProductDetails = storeCodeDiscountBusiness
                        .calculateDiscountToOrderProductDetail(orderProductDetails, storeCodeDiscount);
                orderProductDetailBusiness.saveAllOrderProductDetail(orderProductDetails);
            }
        }
        return orderRepository.save(order);
    }

}
