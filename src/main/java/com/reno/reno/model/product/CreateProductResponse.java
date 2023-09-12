package com.reno.reno.model.product;

import java.util.List;

import com.reno.reno.model.ImageEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateProductResponse extends ProductEntity {
    private List<ImageEntity> images;
}
