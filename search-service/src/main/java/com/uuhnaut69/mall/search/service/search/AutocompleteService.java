package com.uuhnaut69.mall.search.service.search;

import com.uuhnaut69.mall.search.payload.response.AutocompleteResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
public interface AutocompleteService {

    List<AutocompleteResponse> autocomplete(String text, int size) throws IOException;

}
