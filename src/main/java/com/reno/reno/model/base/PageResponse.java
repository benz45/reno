package com.reno.reno.model.base;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResponse<T> {

    private PageInformation pageInformation;

    private boolean first;

    private boolean last;

    private int totalPages;

    private long totalElement;

    private long sequence = new Date().getTime();

    private List<T> entities;

    public static <T> PageResponse<T> create(List<T> listPage, Long count, Pageable pageable, Class<T> cls) {
        Page<T> page = new PageImpl<>(listPage, pageable, count);
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPageInformation(new PageInformation(page.getNumber() + 1,
                page.getSize()));
        pageResponse.totalPages = page.getTotalPages();
        pageResponse.entities = page.getContent();
        pageResponse.first = page.isFirst();
        pageResponse.last = page.isLast();
        pageResponse.totalElement = page.getTotalElements();

        return pageResponse;
    }

}