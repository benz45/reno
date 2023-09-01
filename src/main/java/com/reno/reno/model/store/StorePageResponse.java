package com.reno.reno.model.store;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StorePageResponse {
    private Long id;

    private String name;

    private String detail;

    private String storeProfileUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    private Long following_customers;
}
