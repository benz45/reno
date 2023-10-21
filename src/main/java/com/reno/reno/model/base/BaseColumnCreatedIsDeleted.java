package com.reno.reno.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseColumnCreatedIsDeleted {

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
