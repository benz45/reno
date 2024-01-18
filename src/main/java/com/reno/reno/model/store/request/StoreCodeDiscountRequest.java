package com.reno.reno.model.store.request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StoreCodeDiscountRequest {

    @NotNull(message = "codeText required.")
    private String codeText;

    @NotNull(message = "storeId required.")
    private Long storeId;

    @NotNull(message = "codeAmount required.")
    private Integer codeAmount;

    @NotNull(message = "discountAmount required.")
    private Integer discountAmount;

    @NotNull(message = "codeDiscountTypeId required.")
    private Integer codeDiscountTypeId;

    private Integer minPurchase;

    private Integer maxDiscount;

    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startCodeDiscountDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expridDate;

}
