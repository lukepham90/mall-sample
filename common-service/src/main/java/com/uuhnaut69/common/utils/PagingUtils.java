package com.uuhnaut69.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author uuhnaut
 * @project mall
 */
public final class PagingUtils {

    private PagingUtils() {
    }

    public static Pageable makePageRequest(String sortBy, String order, int page, int pageSize) {
        Sort sort =
                "asc".equalsIgnoreCase(order) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(page - 1, pageSize, sort);
    }
}
