package com.uuhnaut69.mall.search.service.search;

import com.uuhnaut69.mall.search.payload.response.AutocompleteResponse;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
public interface AutocompleteService {

    /**
     * Autocomplete product
     *
     * @param text Search content {@link String}
     * @param size Result size {@link Integer}
     * @return List {@link AutocompleteResponse}
     * @throws Exception
     */
    List<AutocompleteResponse> autocomplete(String text, int size) throws Exception;

}
