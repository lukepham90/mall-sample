package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.common.utils.PagingUtils;
import com.uuhnaut69.core.domain.model.Product;
import com.uuhnaut69.core.mapper.ProductMapper;
import com.uuhnaut69.core.payload.request.IdsRequest;
import com.uuhnaut69.core.payload.request.ProductRequest;
import com.uuhnaut69.core.payload.response.ProductResponse;
import com.uuhnaut69.core.service.product.ProductService;
import com.uuhnaut69.core.service.user.UserService;
import com.uuhnaut69.search.document.ProductEs;
import com.uuhnaut69.search.service.search.ProductRecommendationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.uuhnaut69.security.jwt.SecurityUtils.getCurrentUserId;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Product", value = "Product Endpoint")
public class ProductController {

  private final ProductService productService;

  private final ProductMapper productMapper;

  private final UserService userService;

  private final ProductRecommendationService productRecommendationService;

  @ApiOperation(value = "Get Products Endpoint", notes = "Public endpoint")
  @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL)
  public GenericResponse getPublicProductPage(
      @RequestParam(value = "sort", defaultValue = "id") String sortBy,
      @RequestParam(value = "order", defaultValue = "desc") String order,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

    if (getCurrentUserId().isPresent()) {
      Pageable pageable = PageRequest.of(page - 1, pageSize);
      Page<ProductEs> products =
          productRecommendationService.recommendation(
              getCurrentUserId().get().toString(), pageable);
      return GenericResponse.builder().data(products.getContent()).build();
    }
    return getGenericResponse(sortBy, order, page, pageSize);
  }

  @ApiOperation(value = "Get Product Detail Endpoint", notes = "Public endpoint")
  @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{id}")
  public GenericResponse getPublicProductDetail(@PathVariable UUID id) {

    Product product = productService.findById(id);
    ProductResponse productResponse = productMapper.toProductResponse(product);

    if (getCurrentUserId().isPresent()) {
      userService.markAsReadProduct(getCurrentUserId().get(), product.getId());
    }
    return GenericResponse.builder().data(productResponse).build();
  }

  @ApiOperation(value = "Get Products Endpoint", notes = "Admin endpoint")
  @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
  public GenericResponse getAdminProductPage(
      @RequestParam(value = "sort", defaultValue = "id") String sortBy,
      @RequestParam(value = "order", defaultValue = "desc") String order,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    return getGenericResponse(sortBy, order, page, pageSize);
  }

  private GenericResponse getGenericResponse(String sortBy, String order, int page, int pageSize) {

    Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
    Page<Product> products = productService.findAll(pageable);
    List<ProductResponse> list = productMapper.toListProductResponse(products.getContent());
    return GenericResponse.builder().data(list).build();
  }

  @ApiOperation(value = "Get Product Detail Endpoint", notes = "Admin endpoint")
  @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
  public GenericResponse getAdminProductDetail(@PathVariable UUID id) {
    Product product = productService.findById(id);
    ProductResponse productResponse = productMapper.toProductResponse(product);
    return GenericResponse.builder().data(productResponse).build();
  }

  @ApiOperation(value = "Create A Product Endpoint", notes = "Admin endpoint")
  @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
  public GenericResponse create(@RequestBody @Valid ProductRequest productRequest) {
    Product product = productService.create(productRequest);
    ProductResponse productResponse = productMapper.toProductResponse(product);
    return GenericResponse.builder().data(productResponse).build();
  }

  @ApiOperation(value = "Update A Product Endpoint", notes = "Admin endpoint")
  @PutMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
  public GenericResponse update(
      @PathVariable UUID id, @RequestBody @Valid ProductRequest productRequest) {
    Product product = productService.update(id, productRequest);
    ProductResponse productResponse = productMapper.toProductResponse(product);
    return GenericResponse.builder().data(productResponse).build();
  }

  @ApiOperation(value = "Delete A Product Endpoint", notes = "Admin endpoint")
  @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
  public GenericResponse delete(@PathVariable UUID id) {
    productService.delete(id);
    return new GenericResponse();
  }

  @ApiOperation(value = "Delete Products Endpoint", notes = "Admin endpoint")
  @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
  public GenericResponse deleteAll(@RequestBody IdsRequest idsRequest) {
    productService.deleteAll(idsRequest.getIds());
    return new GenericResponse();
  }
}
