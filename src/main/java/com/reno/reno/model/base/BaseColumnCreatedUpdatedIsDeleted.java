package com.reno.reno.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseColumnCreatedUpdatedIsDeleted extends BaseColumnCreatedUpdated {

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
