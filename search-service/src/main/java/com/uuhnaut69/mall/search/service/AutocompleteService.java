package com.uuhnaut69.mall.search.service;

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
     * @param text
     * @param size
     * @return List AutocompleteResponse
     * @throws Exception
     */
    List<AutocompleteResponse> autocomplete(String text, int size) throws Exception;

}
