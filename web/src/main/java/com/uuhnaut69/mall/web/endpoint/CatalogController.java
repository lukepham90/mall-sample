package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.core.utils.PagingUtils;
import com.uuhnaut69.mall.domain.model.Catalog;
import com.uuhnaut69.mall.mapper.CatalogMapper;
import com.uuhnaut69.mall.payload.request.CatalogRequest;
import com.uuhnaut69.mall.payload.request.IdsRequest;
import com.uuhnaut69.mall.payload.response.CatalogResponse;
import com.uuhnaut69.mall.service.catalog.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Catalog", value = "Catalog Endpoint")
public class CatalogController {

    private final CatalogMapper catalogMapper;
    private final CatalogService catalogService;

    /**
     * Get catalog page
     *
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Catalogs Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.CATALOG_URL)
    public GenericResponse getCatalogPage(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                          @RequestParam(value = "order", defaultValue = "desc") String order,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Catalog> catalogs = catalogService.findAll(pageable);
        List<CatalogResponse> list = catalogMapper.toListCatalogResponse(catalogs.getContent());
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Create new catalog
     *
     * @param catalogRequest
     * @return GenericResponse
     */
    @ApiOperation(value = "Create A Catalog Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CATALOG_URL)
    public GenericResponse create(@RequestBody @Valid CatalogRequest catalogRequest) {
        CatalogResponse catalogResponse = catalogMapper.toCatalogResponse(catalogService.create(catalogRequest));
        return GenericResponse.builder().data(catalogResponse).build();
    }

    /**
     * Update catalog
     *
     * @param id
     * @param catalogRequest
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Update A Catalog Endpoint", notes = "Admin endpoint")
    @PutMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CATALOG_URL + "/{id}")
    public GenericResponse update(@PathVariable UUID id, @RequestBody @Valid CatalogRequest catalogRequest)
            throws Exception {
        CatalogResponse catalogResponse = catalogMapper.toCatalogResponse(catalogService.update(id, catalogRequest));
        return GenericResponse.builder().data(catalogResponse).build();
    }

    /**
     * Delete catalog
     *
     * @param id
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Delete A Catalog Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CATALOG_URL + "/{id}")
    public GenericResponse delete(@PathVariable UUID id) throws Exception {
        catalogService.delete(id);
        return new GenericResponse();
    }

    /**
     * Delete catalogs by given ids
     *
     * @param idsRequest
     * @return GenericResponse
     */
    @ApiOperation(value = "Delete Catalogs Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CATALOG_URL)
    public GenericResponse deleteAll(@RequestBody IdsRequest idsRequest) {
        catalogService.deleteAll(idsRequest.getIds());
        return new GenericResponse();
    }
}