package com.reno.reno.model.store;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.AddressEntiry;

import lombok.Data;

@Data
public class CreateStoreResponse {
    private Long id;

    private String storeName;

    private String detail;

    private List<StoreImageEntity> storeImages;

    private AddressEntiry address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private Date updatedAt;
}
