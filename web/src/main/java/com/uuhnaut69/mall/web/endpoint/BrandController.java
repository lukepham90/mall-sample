package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.core.utils.PagingUtils;
import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.mapper.BrandMapper;
import com.uuhnaut69.mall.payload.request.BrandRequest;
import com.uuhnaut69.mall.payload.request.IdsRequest;
import com.uuhnaut69.mall.payload.response.BrandResponse;
import com.uuhnaut69.mall.service.brand.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Brand", value = "Brand Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class BrandController {

    private final BrandMapper brandMapper;
    private final BrandService brandService;

    /**
     * Get brand page
     *
     * @param sortBy   Sorted field
     * @param order    Ordered field
     * @param page     Page number
     * @param pageSize Page size
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Brands Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.BRAND_URL)
    public GenericResponse getBrandPage(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                        @RequestParam(value = "order", defaultValue = "desc") String order,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Brand> brands = brandService.findAll(pageable);
        List<BrandResponse> list = brandMapper.toListBrandResponse(brands.getContent());
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Create new brand
     *
     * @param brandRequest {@link BrandRequest}
     * @return GenericResponse
     */
    @ApiOperation(value = "Create Brand Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.BRAND_URL)
    public GenericResponse create(@RequestBody BrandRequest brandRequest) {
        log.debug("Insert new brand {} into database", brandRequest);
        Brand brand = brandService.create(brandRequest);
        return GenericResponse.builder().data(brandMapper.toBrandResponse(brand)).build();
    }

    /**
     * Update brand
     *
     * @param id           Brand Id
     * @param brandRequest {@link BrandRequest}
     * @return GenericResponse
     */
    @ApiOperation(value = "Update Brand Endpoint", notes = "Admin endpoint")
    @PutMapping(path = UrlConstants.ADMIN_URL + UrlConstants.BRAND_URL + "/{id}")
    public GenericResponse update(@PathVariable UUID id, @RequestBody BrandRequest brandRequest) {
        log.info("Update brand {} with {} into database", id, brandRequest);
        Brand brand = brandService.update(id, brandRequest);
        return GenericResponse.builder().data(brandMapper.toBrandResponse(brand)).build();
    }

    /**
     * Delete a brand
     *
     * @param id Brand Id
     * @return GenericResponse
     */
    @ApiOperation(value = "Delete Brand Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.BRAND_URL + "/{id}")
    public GenericResponse delete(@PathVariable UUID id) {
        log.debug("Delete a brand {} from database", id);
        brandService.delete(id);
        return new GenericResponse();
    }

    /**
     * Delete brands by given ids
     *
     * @param idsRequest List id request
     * @return GenericResponse
     */
    @ApiOperation(value = "Delete Brands Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.BRAND_URL)
    public GenericResponse deleteAll(@RequestBody IdsRequest idsRequest) {
        log.debug("Delete brands {} from database", idsRequest.getIds());
        brandService.deleteAll(idsRequest.getIds());
        return new GenericResponse();
    }

}