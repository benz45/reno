package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.reno.reno.constant.ProductStatusConstant;
import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.CreateProductRequest;
import com.reno.reno.model.product.CreateProductResponse;
import com.reno.reno.model.product.ProductDetailTypeEntity;
import com.reno.reno.model.product.ProductEntity;
import com.reno.reno.model.product.ProductPageResponse;
import com.reno.reno.model.product.ProductStatusEntity;
import com.reno.reno.model.product.ProductTypeEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.repository.BaseCustomRepository;
import com.reno.reno.repository.product.ProductRepository;
import com.reno.reno.repository.product.ProductTypeRepository;
import com.reno.reno.util.Util;

@Component
public class ProductBusiness extends BaseCustomRepository {

    private @Autowired ProductDetailTypeBusiness productDetailTypeBusiness;
    private @Autowired ProductStatusBusiness productStatusBusiness;
    private @Autowired ProductImageBusiness productImageBusiness;
    private @Autowired ProductRepository productRepository;
    private @Autowired ProductTypeRepository productTypeRepository;
    private @Autowired PageCustomBusiness pageCustomBusiness;

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

    public String generateQueryStringGetProduct(String productName, Pageable pageable) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct p.id, p.name, p.price, p.status_id, p.updated_at, p.created_at, i.id as image_id, i.key as product_image_key from ecommerce_store.product p ");
        s.append(generateQueryStringGetProductFrom());
        s.append(generateQueryStringGetProductFilter(productName));
        s.append(" group by p.id, i.id ");
        s.append(generateQueryStringGetProductOrder());
        s.append(pageCustomBusiness.generatePaginationQuery(pageable));
        return s.toString();
    }

    public String generateQueryStringGetProductCount(String productName, Pageable pageable) {
        StringBuilder s = new StringBuilder();
        s.append("select count(distinct p.id) from ecommerce_store.product p ");
        s.append(generateQueryStringGetProductFrom());
        s.append(generateQueryStringGetProductFilter(productName));
        return s.toString();
    }

    public String generateQueryStringGetProductFilter(String productName) {
        StringBuilder s = new StringBuilder();
        if (Util.isNotNull(productName)) {
            s.append(" and (lower(p.name) like '%" + productName + "%') ");
        }
        return s.toString();
    }

    public String generateQueryStringGetProductOrder() {
        StringBuilder s = new StringBuilder();
        s.append(" order by p.price asc ");
        return s.toString();
    }

    public String generateQueryStringGetProductFrom() {
        StringBuilder s = new StringBuilder();
        s.append(

                "left join ecommerce_store.product_image pi2 on pi2.product_id = p.id and pi2.is_deleted = false "
                        + "left join ecommerce_store.image i on i.id = pi2.image_id and i.is_deleted = false "
                        + "join ecommerce_store.store s on s.id = p.store_id and s.is_deleted = false where true "
                        + "and p.is_active = true ");
        return s.toString();
    }

    public List<ProductPageResponse> convertQueryResultToProductPageResponse(List<Object[]> queryResults) {
        List<ProductPageResponse> productPageResponses = new ArrayList<>();
        for (Object[] queryResult : queryResults) {
            productPageResponses.add(extractProductPageResponse(queryResult));
        }
        return productPageResponses;
    }

    public ProductPageResponse extractProductPageResponse(Object[] queryResult) {
        ProductPageResponse productPageResponse = new ProductPageResponse();
        productPageResponse.setId(super.convertToUniqueType(queryResult[0]));
        productPageResponse.setName(super.convertToUniqueType(queryResult[1]));
        productPageResponse.setPrice(super.convertToUniqueType(queryResult[2]));
        productPageResponse.setStatusId(super.convertToUniqueType(queryResult[3]));
        productPageResponse.setUpdatedAt(super.convertToUniqueType(queryResult[4]));
        productPageResponse.setCreatedAt(super.convertToUniqueType(queryResult[5]));
        productPageResponse.setImageId(super.convertToUniqueType(queryResult[6]));
        productPageResponse.setProductImageKey(super.convertToUniqueType(queryResult[7]));
        return productPageResponse;
    }

}
