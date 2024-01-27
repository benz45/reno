package com.reno.reno.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.constant.CodeDiscountTypeConstant;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderProductDetailEntity;
import com.reno.reno.model.store.CodeDiscountTypeEntity;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.request.StoreCodeDiscountRequest;
import com.reno.reno.repository.store.StoreCodeDiscountRepository;
import com.reno.reno.util.Util;

@Component
public class StoreCodeDiscountBusiness {
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired StoreCodeDiscountRepository storeCodeDiscountRepository;
    private @Autowired CodeDiscountTypeBusiness codeDiscountTypeBusiness;

    public StoreCodeDiscountEntity shouldGetStoreCodeDiscountByIdOrElseThrows(Long storeCodeDiscountId)
            throws ApiException {
        return storeCodeDiscountRepository.findById(storeCodeDiscountId)
                .orElseThrow(() -> new ApiException("400",
                        "Can't find store code discount id: " + storeCodeDiscountId.toString()));
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StoreCodeDiscountEntity> createStoreCodeDiscount(List<StoreCodeDiscountRequest> requests)
            throws ApiException {
        List<StoreCodeDiscountEntity> storeCodeDiscounts = new ArrayList<StoreCodeDiscountEntity>();
        for (StoreCodeDiscountRequest request : requests) {
            StoreCodeDiscountEntity storeCodeDiscount = new StoreCodeDiscountEntity();
            StoreEntity store = storeBusiness.shouldGetStoreByIdOrElseThrow(request.getStoreId());
            CodeDiscountTypeEntity codeDiscountType = codeDiscountTypeBusiness
                    .shouldGetCodeDiscountByIdOrElseThrows(request.getCodeDiscountTypeId());
            storeCodeDiscount.setStore(store);
            storeCodeDiscount.setCodeText(request.getCodeText());
            storeCodeDiscount.setCodeAmount(request.getCodeAmount());
            storeCodeDiscount.setDiscountAmount(request.getDiscountAmount());
            storeCodeDiscount.setCodeDiscountType(codeDiscountType);
            if (Util.isNotNull(request.getMinPurchase()) && request.getMinPurchase() > 0) {
                storeCodeDiscount.setMinAmount(request.getMinPurchase());
            }
            if (Util.isNotNull(request.getMaxDiscount()) && request.getMaxDiscount() > 0) {
                storeCodeDiscount.setMaxDiscount(request.getMaxDiscount());
            }
            if (Util.isNotNull(request.getExpridDate()) && request.getExpridDate().compareTo(new Date()) > 0) {
                storeCodeDiscount.setExpridDate(request.getExpridDate());
            }
            storeCodeDiscount.setIsActive(request.getIsActive());
            storeCodeDiscount.setCreatedAt(new Date());
            storeCodeDiscount.setUpdatedAt(new Date());
            storeCodeDiscounts.add(storeCodeDiscount);
        }
        return storeCodeDiscountRepository.saveAll(storeCodeDiscounts);
    }

    public List<OrderProductDetailEntity> calculateDiscountToOrderProductDetail(
            List<OrderProductDetailEntity> orderProductDetails,
            StoreCodeDiscountEntity storeCodeDiscount)
            throws ApiException {
        checkDiscountRemainingOrElseThrow(storeCodeDiscount);
        Integer totalPriceProducts = getTotalProductPrice(orderProductDetails);
        checkOrderPriceLowerThanMinimumDiscountOrElseThrow(totalPriceProducts, storeCodeDiscount);
        // Bath
        if (storeCodeDiscount.getCodeDiscountType().getId() == CodeDiscountTypeConstant.BATH) {
            orderProductDetails = calculateDiscountTypeBathToOrderProductDetail(totalPriceProducts, orderProductDetails,
                    storeCodeDiscount);
            storeCodeDiscount = updateUsedCodeAmount(storeCodeDiscount);
            return orderProductDetails;
        }
        // Percent
        if (storeCodeDiscount.getCodeDiscountType().getId() == CodeDiscountTypeConstant.PERCENT) {
            orderProductDetails = calculateDiscountTypePercentToOrderProductDetail(totalPriceProducts,
                    orderProductDetails,
                    storeCodeDiscount);
            storeCodeDiscount = updateUsedCodeAmount(storeCodeDiscount);
            return orderProductDetails;
        }
        return orderProductDetails;
    }

    public List<OrderProductDetailEntity> calculateDiscountTypeBathToOrderProductDetail(
            Integer totalPriceProducts,
            List<OrderProductDetailEntity> orderProductDetails,
            StoreCodeDiscountEntity storeCodeDiscount) {
        for (OrderProductDetailEntity orderProductDetail : orderProductDetails) {
            Integer totalPriceProduct = (orderProductDetail.getPrice() * orderProductDetail.getAmount());
            double percent = (double) totalPriceProduct / (double) totalPriceProducts;
            Double resultBigDecimal = convertoBigDecimal(percent);
            double resultDiscountAmount = storeCodeDiscount.getDiscountAmount() * resultBigDecimal;
            orderProductDetail.setDiscount((int) resultDiscountAmount);
        }
        return orderProductDetails;
    }

    public List<OrderProductDetailEntity> calculateDiscountTypePercentMaxDiscountToOrderProductDetail(
            Integer totalPriceProducts,
            List<OrderProductDetailEntity> orderProductDetails,
            StoreCodeDiscountEntity storeCodeDiscount) {
        for (OrderProductDetailEntity orderProductDetail : orderProductDetails) {
            Integer totalPriceProduct = (orderProductDetail.getPrice() * orderProductDetail.getAmount());
            double percent = (double) totalPriceProduct / (double) totalPriceProducts;
            Double resultBigDecimal = convertoBigDecimal(percent);
            double resultDiscountAmount = storeCodeDiscount.getMaxDiscount() * resultBigDecimal;
            orderProductDetail.setDiscount((int) resultDiscountAmount);
        }
        return orderProductDetails;
    }

    public List<OrderProductDetailEntity> calculateDiscountTypePercentToOrderProductDetail(
            Integer totalPriceProducts,
            List<OrderProductDetailEntity> orderProductDetails,
            StoreCodeDiscountEntity storeCodeDiscount) {
        if (storeCodeDiscount.getMaxDiscount() != null && (totalPriceProducts / 100)
                * storeCodeDiscount.getDiscountAmount() > storeCodeDiscount.getMaxDiscount()) {
            return calculateDiscountTypePercentMaxDiscountToOrderProductDetail(totalPriceProducts, orderProductDetails,
                    storeCodeDiscount);
        }
        for (OrderProductDetailEntity orderProductDetail : orderProductDetails) {
            Integer totalPriceProduct = (orderProductDetail.getPrice() * orderProductDetail.getAmount());
            double percent = (totalPriceProduct / 100) * storeCodeDiscount.getDiscountAmount();
            double resultBigDecimal = convertoBigDecimal(percent);
            orderProductDetail.setDiscount((int) resultBigDecimal);
        }
        return orderProductDetails;
    }

    public StoreCodeDiscountEntity updateUsedCodeAmount(StoreCodeDiscountEntity storeCodeDiscount) {
        storeCodeDiscount.setUsedCodeAmount(storeCodeDiscount.getUsedCodeAmount() + 1);
        storeCodeDiscount.setUpdatedAt(new Date());
        return storeCodeDiscount;
    }

    public Integer getTotalProductPrice(List<OrderProductDetailEntity> orderProductDetails) {
        Integer totalPriceProducts = 0;
        for (OrderProductDetailEntity orderProductDetail : orderProductDetails) {
            totalPriceProducts += orderProductDetail.getPrice() * orderProductDetail.getAmount();
        }
        return totalPriceProducts;
    }

    public void checkDiscountRemainingOrElseThrow(StoreCodeDiscountEntity storeCodeDiscount) throws ApiException {
        if (storeCodeDiscount.getUsedCodeAmount() == storeCodeDiscount.getCodeAmount()) {
            throw new ApiException("400", "The discount has been used up.");
        }
    }

    public void checkOrderPriceLowerThanMinimumDiscountOrElseThrow(Integer totalPriceProducts,
            StoreCodeDiscountEntity storeCodeDiscount) throws ApiException {
        if (storeCodeDiscount.getMinAmount() != null && totalPriceProducts < storeCodeDiscount.getMinAmount()) {
            throw new ApiException("400", "The order price is lower than the minimum discount price.");
        }
    }

    public Double convertoBigDecimal(double persent) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Double
                .valueOf((decimalFormat
                        .format(BigDecimal.valueOf(persent).setScale(2, RoundingMode.HALF_UP))));
    }
}
