package com.reno.reno.model.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseColumnCreatedAtCreatedByIsDeleted extends BaseColumnCreatedIsDeleted {

    @Column(name = "created_by")
    private Integer created_by;

}
