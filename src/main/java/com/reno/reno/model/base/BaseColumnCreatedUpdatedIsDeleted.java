package com.reno.reno.model.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseColumnCreatedUpdatedIsDeleted extends BaseColumnCreatedUpdated {

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
