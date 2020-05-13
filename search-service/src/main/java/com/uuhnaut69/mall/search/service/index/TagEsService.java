package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.TagEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TagEsService {

    void handleCdcEvent(Map<String, Object> tagData, Operation operation);

    TagEs findById(String id);
}
