package com.reno.reno.model.common;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResponse<T> {
    private PageInfomation pageInfomation;
    private boolean isFirst;
    private boolean isLast;
    private int totalPages;
    private int totalElement;
    private long sequence = new Date().getTime();
    private List<T> entities;

    public static <T> PageResponse<T> create(Page<T> page, List<T> entities) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageInfomation(new PageInfomation(page.getNumber() + 1, page.getSize()));
        pageResponse.totalElement = page.getTotalPages();
        pageResponse.entities = entities;
        pageResponse.isFirst = page.isFirst();
        pageResponse.isLast = page.isLast();
        pageResponse.totalPages = page.getTotalPages();
        return pageResponse;
    }

    public static <T> PageResponse<T> createResponse(List<T> responses, Long totalAmount, Pageable pageable) {
        var page = new PageImpl<>(responses, pageable, totalAmount);
        return create(page, page.getContent());
    }
}
