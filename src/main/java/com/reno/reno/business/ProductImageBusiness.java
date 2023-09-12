package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.product.CreateProductImageRequest;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.product.ProductImageEntity;
import com.reno.reno.repository.product.ProductImageRepository;
import com.reno.reno.util.Util;

@Component
public class ProductImageBusiness {
    private @Autowired ProductImageRepository productImageRepository;
    private @Autowired ImageBusiness imageBusiness;

    public List<ImageEntity> shouldSaveProductImage(ProductEntity product, CustomerEntity customer,
            List<CreateProductImageRequest> requestImages) {
        List<ImageEntity> images = new ArrayList<ImageEntity>();
        if (Util.isNullOrEmpty(requestImages)) {
            return images;
        }
        List<ProductImageEntity> productImages = new ArrayList<ProductImageEntity>();
        for (CreateProductImageRequest requestImage : requestImages) {
            if (Util.isNotNull(requestImage.getKey())) {
                ImageEntity image = imageBusiness.saveImage(requestImage.getKey(), customer.getId());
                images.add(image);
                ProductImageEntity productImage = new ProductImageEntity();
                productImage.setImage(image);
                productImage.setCreatedAt(new Date());
                productImage.setProduct(product);
                productImages.add(productImage);
            }
        }
        productImageRepository.saveAll(productImages);
        return images;
    }

    public List<ImageEntity> shouldGetProductImage(Long productId) {
        List<ImageEntity> images = new ArrayList<ImageEntity>();
        List<ProductImageEntity> productImages = productImageRepository.findAllByProductId(productId);
        for (ProductImageEntity productImage : productImages) {
            if (Util.isNotNull(productImage.getImage()) & Util.isNotNull(productImage.getImage().getKey())) {
                images.add(productImage.getImage());
            }
        }
        return images;
    }
}
