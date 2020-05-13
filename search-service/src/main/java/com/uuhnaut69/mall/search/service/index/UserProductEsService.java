package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserProductEsService {

    void handleCdcEvent(Map<String, Object> userProductData, Operation operation);
}
