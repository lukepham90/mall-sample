package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.CategoryEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CategoryEsService {

    void handleCdcEvent(Map<String, Object> categoryData, Operation operation);

    CategoryEs findById(String id);

}
