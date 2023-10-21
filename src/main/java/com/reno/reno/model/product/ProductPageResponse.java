package com.reno.reno.model.product;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductPageResponse {

    private Long id;

    private String name;

    private Integer price;

    private Integer statusId;

    private Date updatedAt;

    private Date createdAt;

    private Long imageId;

    private String productImageKey;
}
