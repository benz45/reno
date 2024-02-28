package com.reno.reno.model.base;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseColumnCreatedUpdated {

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private Date updatedAt;
}
