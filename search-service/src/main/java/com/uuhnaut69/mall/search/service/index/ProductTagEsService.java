package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductTagEsService {

    void handleCdcEvent(Map<String, Object> productTagData, Map<String, Object> productTagDataBefore,
                           Operation operation);
}
