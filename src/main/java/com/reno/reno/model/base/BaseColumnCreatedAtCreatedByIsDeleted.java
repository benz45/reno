package com.reno.reno.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseColumnCreatedAtCreatedByIsDeleted extends BaseColumnCreatedIsDeleted {

    @Column(name = "created_by")
    private Long createdBy;

}
