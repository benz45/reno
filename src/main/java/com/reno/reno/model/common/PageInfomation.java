package com.reno.reno.model.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfomation implements Serializable {
    private static final long serialVersionUID = 1L;
    private int number;
    private int size;
}
