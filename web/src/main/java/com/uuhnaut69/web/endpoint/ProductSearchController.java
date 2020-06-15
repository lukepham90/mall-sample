package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.common.utils.PagingUtils;
import com.uuhnaut69.search.document.ProductEs;
import com.uuhnaut69.search.payload.response.AutocompleteResponse;
import com.uuhnaut69.search.service.search.AutocompleteService;
import com.uuhnaut69.search.service.search.ProductSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Search", value = "Search Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class ProductSearchController {

  private final AutocompleteService autocompleteService;
  private final ProductSearchService productSearchService;

  @ApiOperation(value = "Autocomplete Endpoint", notes = "Public endpoint")
  @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.AUTOCOMPLETE_URL)
  public GenericResponse autocomplete(
      @RequestParam(value = "text", defaultValue = "") String text,
      @RequestParam(value = "size", defaultValue = "25") int size)
      throws IOException {

    List<AutocompleteResponse> list = autocompleteService.autocomplete(text, size);
    return GenericResponse.builder().data(list).build();
  }

  @ApiOperation(value = "Search Endpoint", notes = "Public endpoint")
  @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.SEARCH_URL)
  public GenericResponse search(
      @RequestParam(value = "text", defaultValue = "") String text,
      @RequestParam(value = "sort", defaultValue = "createdDate") String sortBy,
      @RequestParam(value = "order", defaultValue = "desc") String order,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

    Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
    Page<ProductEs> products = productSearchService.search(text, pageable);
    return GenericResponse.builder().data(products.getContent()).build();
  }
}
