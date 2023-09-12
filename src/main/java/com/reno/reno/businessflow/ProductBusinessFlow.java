package com.reno.reno.businessflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.business.ProductBusiness;
import com.reno.reno.business.ProductImageBusiness;
import com.reno.reno.business.StoreBusiness;
import com.reno.reno.business.StoreOwnerBusiness;
import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.CreateProductRequest;
import com.reno.reno.model.product.CreateProductResponse;
import com.reno.reno.model.product.ProductDetailTypeEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreOwnerEntity;
import com.reno.reno.util.Util;

@Component
public class ProductBusinessFlow {

    private @Autowired ProductBusiness productBusiness;
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired ProductImageBusiness productImageBusiness;
    private @Autowired StoreOwnerBusiness storeOwnerBusiness;

    @Transactional(rollbackFor = Exception.class)
    public CreateProductResponse shouldCreateProduct(CreateProductRequest request) throws ApiException {
        productBusiness.checkStoreIdIsNullOrElseThrow(request);
        request.setProductStatus(productBusiness.shouldSetProductStatus(request.getProductStatus()));
        StoreEntity store = storeBusiness.shouldGetStoreByIdOrElseThrow(request.getStoreId());
        ProductEntity product = productBusiness.convertToProductEntity(request, store);
        StoreOwnerEntity storeOwner = storeOwnerBusiness.getStoreOwnerByStoreIdOrElseThrow(store.getId());
        product = productBusiness.saveProduct(product);
        List<ProductDetailTypeEntity> productDetailTypes = productBusiness.shouldSaveProductDetailType(
                request.getProductTypes(),
                product);
        product.setProductDetailType(productDetailTypes);
        List<ImageEntity> productImages = productImageBusiness.shouldSaveProductImage(product, storeOwner.getCustomer(),
                request.getProductImages());
        CreateProductResponse response = Util.map(product, CreateProductResponse.class);
        response.setImages(productImages);
        return response;
    }
}
