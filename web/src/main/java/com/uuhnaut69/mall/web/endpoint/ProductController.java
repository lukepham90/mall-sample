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

    @ApiOperation(value = "Get Products Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPage(
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String order,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @CurrentUser UserPrinciple userPrinciple) {
        if (userPrinciple != null) {
            log.info("Get list recommendation product for {}", userPrinciple.getName());
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<ProductEs> products = productRecommendationService.recommendation(userPrinciple.getId().toString(),
                    pageable);
            return GenericResponse.builder().data(products.getContent()).build();
        }
        log.info("Get list public product");
        return getGenericResponse(sortBy, order, page, pageSize);
    }

    @ApiOperation(value = "Get Product Detail Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{id}")
    public GenericResponse getProductDetail(@CurrentUser UserPrinciple userPrinciple, @PathVariable UUID id) {
        Product product = productService.findById(id);
        ProductResponse productResponse = productMapper.toProductResponse(product);
        userService.markAsReadProduct(userPrinciple.getId(), product.getId());
        return GenericResponse.builder().data(productResponse).build();
    }

    @ApiOperation(value = "Get Products Endpoint", notes = "Admin endpoint")
    @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.PRODUCT_URL)
    public GenericResponse getProductPage(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
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
    public GenericResponse getProductDetail(@PathVariable UUID id) {
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
    public GenericResponse update(@PathVariable UUID id, @RequestBody @Valid ProductRequest productRequest) {
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
