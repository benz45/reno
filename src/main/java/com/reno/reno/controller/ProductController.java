package com.reno.reno.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.ProductBusiness;
import com.reno.reno.businessflow.ProductBusinessFlow;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.CreateProductRequest;
import com.reno.reno.model.product.CreateProductResponse;

@Validated
@RestController
public class ProductController {
    private @Autowired ProductBusiness productBusiness;
    private @Autowired ProductBusinessFlow productBusinessFlow;

    @PostMapping("/product")
    public CreateProductResponse postProduct(@Valid @RequestBody CreateProductRequest request) throws ApiException {
        return productBusinessFlow.shouldCreateProduct(request);
    }

    @GetMapping("/product/{id}")
    public CreateProductResponse getProductById(@PathVariable("id") Long id) throws ApiException {
        return productBusiness.shouldGetProductByIdOrElseThrow(id);
    }
}