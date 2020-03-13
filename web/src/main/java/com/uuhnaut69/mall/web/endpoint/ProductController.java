package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.core.utils.PagingUtils;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.mapper.ProductMapper;
import com.uuhnaut69.mall.payload.request.IdsRequest;
import com.uuhnaut69.mall.payload.request.ProductRequest;
import com.uuhnaut69.mall.payload.response.ProductResponse;
import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.service.search.ProductRecommendationService;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.rating.RatingProductService;
import com.uuhnaut69.mall.service.user.UserService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Product", value = "Product Endpoint")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    private final UserService userService;

    private final ProductRecommendationService productRecommendationService;

    private final RatingProductService ratingProductService;

    /**
     * Find product page
     *
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @param userPrinciple
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Get Products Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPage(
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @CurrentUser UserPrinciple userPrinciple) throws Exception {
        if (userPrinciple != null) {
            log.info("Get list recommendation product for {}", userPrinciple.getName());
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<ProductEs> products = productRecommendationService.recommendation(userPrinciple.getId().toString(),
                    pageable);
            return GenericResponse.builder().data(products.getContent()).build();
        }
        log.info("Get list public product");
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Product> products = productService.findAll(pageable);
        List<ProductResponse> list = productMapper.toListProductResponse(products.getContent());
        ratingProductService.getRatingAggregationOfProducts(list);
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Find product page by catalog id
     *
     * @param catalogId
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Products By Catalog Id Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.CATALOG_URL + "/{catalogId}" + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPageByCatalog(@PathVariable UUID catalogId,
                                                   @RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                                   @RequestParam(value = "order", defaultValue = "desc") String order,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Product> products = productService.findAllByCatalogId(pageable, catalogId);
        List<ProductResponse> list = productMapper.toListProductResponse(products.getContent());
        ratingProductService.getRatingAggregationOfProducts(list);
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Find  product page by brand id
     *
     * @param brandId
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Products By Brand Id Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.BRAND_URL + "/{brandId}" + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPageByBrand(@PathVariable UUID brandId,
                                                 @RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                                 @RequestParam(value = "order", defaultValue = "desc") String order,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Product> products = productService.findAllByBrandId(pageable, brandId);
        List<ProductResponse> list = productMapper.toListProductResponse(products.getContent());
        ratingProductService.getRatingAggregationOfProducts(list);
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Get product detail
     *
     * @param id
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Get Product Detail Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{id}")
    public GenericResponse getProductDetail(@CurrentUser UserPrinciple userPrinciple, @PathVariable UUID id)
            throws Exception {
        Product product = productService.findById(id);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        if (userPrinciple != null) {
            userService.markAsReadProduct(userPrinciple.getId(), product.getId());
        }
        ratingProductService.getRatingAggregationOfProduct(productResponse);
        return GenericResponse.builder().data(productResponse).build();
    }

    /**
     * Get product page
     *
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Products Endpoint", notes = "Admin endpoint")
    @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPage(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                          @RequestParam(value = "order", defaultValue = "desc") String order,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Product> products = productService.findAll(pageable);
        List<ProductResponse> list = productMapper.toListProductResponse(products.getContent());
        ratingProductService.getRatingAggregationOfProducts(list);
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Get product detail
     *
     * @param id
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Get Product Detail Endpoint", notes = "Admin endpoint")
    @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
    public GenericResponse getProductDetail(@PathVariable UUID id) throws Exception {
        Product product = productService.findById(id);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        ratingProductService.getRatingAggregationOfProduct(productResponse);
        return GenericResponse.builder().data(productResponse).build();
    }

    /**
     * Create product
     *
     * @param productRequest
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Create A Product Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse create(@RequestBody @Valid ProductRequest productRequest) throws Exception {
        Product product = productService.create(productRequest);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return GenericResponse.builder().data(productResponse).build();
    }

    /**
     * Update product
     *
     * @param id
     * @param productRequest
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Update A Product Endpoint", notes = "Admin endpoint")
    @PutMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
    public GenericResponse update(@PathVariable UUID id, @RequestBody @Valid ProductRequest productRequest)
            throws Exception {
        Product product = productService.update(id, productRequest);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return GenericResponse.builder().data(productResponse).build();
    }

    /**
     * Delete product
     *
     * @param id
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Delete A Product Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL + "/{id}")
    public GenericResponse delete(@PathVariable UUID id) throws Exception {
        productService.delete(id);
        return new GenericResponse();
    }

    /**
     * Delete all product
     *
     * @param idsRequest
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Delete Products Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse deleteAll(@RequestBody IdsRequest idsRequest) throws Exception {
        productService.deleteAll(idsRequest.getIds());
        return new GenericResponse();
    }

    /**
     * Rating a product
     *
     * @param userPrinciple
     * @param id
     * @param rating
     * @return GenericResponse
     * @throws Exception
     */
    @PostMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{id}" + "/{rating}")
    public GenericResponse ratingProduct(@CurrentUser UserPrinciple userPrinciple, @PathVariable UUID id, @PathVariable int rating) throws Exception {
        return GenericResponse.builder().data(ratingProductService.ratingProduct(id, userPrinciple.getId(), rating)).build();
    }

}
