package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.constant.OrderStatusConstant;
import com.reno.reno.constant.PaymentDetailTypeConstant;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderEntity;
import com.reno.reno.model.order.OrderProductDetailEntity;
import com.reno.reno.model.order.OrderStatusEntity;
import com.reno.reno.model.order.OrderStoreEntity;
import com.reno.reno.model.order.request.RequestCreateOrder;
import com.reno.reno.model.order.request.RequestOrder;
import com.reno.reno.model.order.request.RequestProduct;
import com.reno.reno.model.payment.PaymentDetailEntity;
import com.reno.reno.model.payment.PaymentDetailTypeEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.repository.order.OrderRepository;

@Component
public class OrderBusiness {

    private @Autowired OrderRepository orderRepository;
    private @Autowired OrderStatusBusiness orderStatusBusiness;
    private @Autowired CustomerBusiness customerBusiness;
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired PaymentDetailBusiness paymentDetailBusiness;
    private @Autowired PaymentDetailTypeBusiness paymentDetailTypeBusiness;
    private @Autowired OrderStoreBusiness orderStoreBusiness;
    private @Autowired OrderProductDetailBusiness orderProductDetailBusiness;
    private @Autowired ProductBusiness productBusiness;

    public OrderEntity shouldSetOrderStatus(PaymentDetailTypeEntity paymentDetailType, OrderEntity order)
            throws ApiException {
        if (paymentDetailType.getId().equals(Integer.valueOf(PaymentDetailTypeConstant.CASH_ON_DELIVERY))) {
            OrderStatusEntity orderStatus = orderStatusBusiness
                    .shouldGetOrderStatusById(OrderStatusConstant.TO_PAY);
            order.setOrderStatus(orderStatus);
        } else {
            OrderStatusEntity orderStatus = orderStatusBusiness
                    .shouldGetOrderStatusById(OrderStatusConstant.TO_SHIP);
            order.setOrderStatus(orderStatus);
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderEntity createOrder(RequestCreateOrder requestCreateOrder) throws ApiException {
        CustomerEntity customer = customerBusiness.getCustomerById(requestCreateOrder.getCustomerId());
        OrderEntity order = new OrderEntity();
        order.setCreatedBy(customer.getId());
        PaymentDetailTypeEntity paymentDetailType = paymentDetailTypeBusiness.shouldGetPaymentDetailTypeByIdOrElseThrow(
                requestCreateOrder.getPaymentDetailTypeId());
        PaymentDetailEntity paymentDetail = paymentDetailBusiness.shouldSaveNewPaymentDetail(paymentDetailType);
        order = shouldSetOrderStatus(paymentDetailType, order);
        order.setPaymentDetail(paymentDetail);
        for (RequestOrder _order : requestCreateOrder.getOrders()) {
            StoreEntity store = storeBusiness.shouldGetStoreByIdOrElseThrow(_order.getStoreId());
            OrderStoreEntity orderStore = orderStoreBusiness.convertToNewOrderStore(order, store);
            orderStore = orderStoreBusiness.shouldSetStoreCodeDiscount(_order.getStoreCodeDiscountId(), orderStore);
            orderStore = orderStoreBusiness.saveOrderStore(orderStore);
            for (RequestProduct _product : _order.getProducts()) {
                productBusiness.shouldSetProductAmountWithOrderAmount(_product.getProductId(), _product.getAmount());
                ProductEntity product = productBusiness.shouldGetProductByIdOrElseThrow(_product.getProductId());
                OrderProductDetailEntity orderProductDetail = orderProductDetailBusiness
                        .convertNewOrderProductDetail(product, _product.getAmount(), orderStore);
                orderProductDetail = orderProductDetailBusiness.saveOrderProductDetail(orderProductDetail);
            }
        }
        return orderRepository.save(order);
    }

}
