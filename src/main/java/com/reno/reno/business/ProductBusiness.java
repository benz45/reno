package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.constant.ProductStatusConstant;
import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.CreateProductRequest;
import com.reno.reno.model.product.CreateProductResponse;
import com.reno.reno.model.product.ProductDetailTypeEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.product.ProductStatusEntity;
import com.reno.reno.model.product.ProductTypeEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.repository.product.ProductRepository;
import com.reno.reno.repository.product.ProductTypeRepository;
import com.reno.reno.util.Util;

@Component
public class ProductBusiness {
    private @Autowired ProductDetailTypeBusiness productDetailTypeBusiness;
    private @Autowired ProductStatusBusiness productStatusBusiness;
    private @Autowired ProductImageBusiness productImageBusiness;
    private @Autowired ProductRepository productRepository;
    private @Autowired ProductTypeRepository productTypeRepository;

    public CreateProductResponse shouldGetProductByIdOrElseThrow(Long productId) throws ApiException {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException("400", "Can't find product id " + productId.toString()));
        CreateProductResponse response = Util.map(product, CreateProductResponse.class);
        List<ImageEntity> images = productImageBusiness.shouldGetProductImage(productId);
        response.setImages(images);
        return response;
    }

    public List<ProductDetailTypeEntity> shouldSaveProductDetailType(List<ProductTypeEntity> productTypes,
            ProductEntity product) {
        List<Integer> productTypeIds = new ArrayList<>();
        for (ProductTypeEntity productType : productTypes) {
            productTypeIds.add(productType.getId());
        }
        productTypes = productTypeRepository.findAllById(productTypeIds);
        List<ProductDetailTypeEntity> productDetailTypes = new ArrayList<>();
        for (ProductTypeEntity productType : productTypes) {
            ProductDetailTypeEntity productDetailType = new ProductDetailTypeEntity();
            productDetailType.setProductType(productType);
            productDetailType.setProduct(product);
            productDetailType.setCreatedAt(new Date());
            productDetailTypes.add(productDetailType);
        }
        return productDetailTypeBusiness.saveAllProductDetailType(productDetailTypes);
    }

    public ProductStatusEntity shouldSetProductStatus(ProductStatusEntity productStatus) throws ApiException {
        if (Util.isNotNull(productStatus) && Util.isNotNull(productStatus.getId())) {
            return productStatusBusiness
                    .shouldGetProductStatusByIdOrElseThrow(productStatus.getId());
        }
        return productStatusBusiness
                .shouldGetProductStatusByIdOrElseThrow(ProductStatusConstant.READY_FOR_SALE_SOON);
    }

    public void checkStoreIdIsNullOrElseThrow(CreateProductRequest request) throws ApiException {
        if (!Util.isNotNull(request.getStoreId())) {
            throw new ApiException("400", "Store id is null.");
        }
    }

    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity convertToProductEntity(CreateProductRequest request, StoreEntity store) {
        ProductEntity product = new ProductEntity();
        Util.setIfNotNull(request.getName(), product::setName);
        Util.setIfNotNull(request.getDetail(), product::setDetail);
        Util.setIfNotNull(request.getPrice(), product::setPrice);
        Util.setIfNotNull(request.getAmount(), product::setAmount);
        Util.setIfNotNull(request.getIsActive(), product::setIsActive);
        Util.setIfNotNull(request.getProductStatus(), product::setProductStatus);
        Util.setIfNotNull(request.getProductStatus(), product::setProductStatus);
        Util.setIfNotNull(store, product::setStore);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        return product;
    }
}
