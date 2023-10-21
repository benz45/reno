package com.reno.reno.model.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInformation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int number;

    private int size;

}
