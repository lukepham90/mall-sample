package com.uuhnaut69.search.service.index;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.TagEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TagEsService {

    void handleCdcEvent(Map<String, Object> tagData, Operation operation);

    TagEs findById(String id);
}
