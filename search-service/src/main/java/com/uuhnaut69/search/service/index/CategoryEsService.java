package com.uuhnaut69.search.service.index;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.CategoryEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CategoryEsService {

    void handleCdcEvent(Map<String, Object> categoryData, Operation operation);

    CategoryEs findById(String id);

}
