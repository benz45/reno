package com.reno.reno.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseColumnCreatedUpdated {

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;
}
