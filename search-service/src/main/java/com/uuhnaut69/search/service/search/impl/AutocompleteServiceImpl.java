package com.uuhnaut69.search.service.search.impl;

import com.uuhnaut69.search.constant.EsConstants;
import com.uuhnaut69.search.payload.response.AutocompleteResponse;
import com.uuhnaut69.search.service.search.AutocompleteService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@RequiredArgsConstructor
public class AutocompleteServiceImpl implements AutocompleteService {

    private final RestHighLevelClient restClient;

    @Override
    public List<AutocompleteResponse> autocomplete(String text, int size) throws IOException {
        SearchRequest searchRequest = makeAutocompletionRequest(text, size);
        SearchResponse searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);
        return extractResponse(searchResponse);
    }

    /**
     * Make autocompletion request
     *
     * @param text Search content
     * @param size Result size
     * @return SearchRequest
     */
    private SearchRequest makeAutocompletionRequest(String text, int size) {
        SearchRequest searchRequest = new SearchRequest(EsConstants.PRODUCT_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        CompletionSuggestionBuilder completeSuggestionBuilder = new CompletionSuggestionBuilder(
                EsConstants.PRODUCT_SUGGEST).size(size).prefix(text, Fuzziness.AUTO).skipDuplicates(true)
                .analyzer(EsConstants.STANDARD_ANALYZER);
        sourceBuilder
                .suggest(new SuggestBuilder().addSuggestion(EsConstants.PRODUCT_SUGGEST, completeSuggestionBuilder));
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }

    /**
     * Extract data from responses
     *
     * @param searchResponse Search response
     * @return Lis {@link AutocompleteResponse}
     */
    private List<AutocompleteResponse> extractResponse(SearchResponse searchResponse) {
        List<AutocompleteResponse> list = new ArrayList<>();
        Suggestion<Entry<Option>> suggestion = searchResponse.getSuggest().getSuggestion(EsConstants.PRODUCT_SUGGEST);
        suggestion.getEntries().forEach(entry -> entry.getOptions()
                .forEach(option -> list.add(new AutocompleteResponse(option.getText().toString()))));
        return list;
    }
}
