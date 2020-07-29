package com.uuhnaut69.search.service.index;

import com.uuhnaut69.common.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserProductEsService {

    void handleCdcEvent(Map<String, Object> userProductData, Operation operation);
}
