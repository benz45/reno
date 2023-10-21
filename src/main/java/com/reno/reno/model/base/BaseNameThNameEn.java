package com.reno.reno.model.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseNameThNameEn {
    @Column(name = "name_th")
    private String nameTh;

    @Column(name = "name_en")
    private String nameEn;
}
