package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserTagEsService {

    void handleCdcEvent(Map<String, Object> userTagData, Map<String, Object> userTagDataBefore, Operation operation);
}
