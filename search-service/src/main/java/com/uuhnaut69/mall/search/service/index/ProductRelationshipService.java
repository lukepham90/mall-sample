package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductRelationshipService {

    void handleCdcEvent(Map<String, Object> productDataAfter, Map<String, Object> productDataBefore,
                        Operation operation);
}
