package com.reno.reno.business;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageCustomBusiness {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    public String generatePaginationQuery(Pageable pageable) {
        StringBuilder query = new StringBuilder();
        Integer pageSize = getPageSizeIfRequestExist(pageable);
        query.append(" limit " + pageSize);
        query.append(" offset " + getOffset(pageSize, getPageNumber(pageable.getPageNumber())));
        return query.toString();
    }

    public Integer getPageSizeIfRequestExist(Pageable pageable) {
        if (pageable != null) {
            return pageable.getPageSize();
        }
        return DEFAULT_PAGE_SIZE;
    }

    public Integer getOffset(Integer pageSize, Integer pageNumber) {
        return pageSize * (pageNumber);
    }

    public Integer getPageNumber(Integer pageNumber) {
        if (pageNumber <= DEFAULT_PAGE_NUMBER) {
            return DEFAULT_PAGE_NUMBER;
        }
        return pageNumber;
    }
}
