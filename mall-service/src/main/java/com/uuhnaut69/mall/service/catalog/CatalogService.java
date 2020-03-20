package com.uuhnaut69.mall.service.catalog;

import com.uuhnaut69.mall.domain.model.Catalog;
import com.uuhnaut69.mall.payload.request.CatalogRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CatalogService {
    /**
     * find catalog pages
     *
     * @param pageable {@link Pageable}
     * @return Catalog page
     */
    Page<Catalog> findAll(Pageable pageable);

    /**
     * find catalog by id
     *
     * @param id {@link UUID}
     * @return Catalog
     */
    Catalog findById(UUID id);

    /**
     * Create a catalog
     *
     * @param catalogRequest {@link CatalogRequest}
     * @return Catalog
     */
    Catalog create(CatalogRequest catalogRequest);

    /**
     * Update Catalog
     *
     * @param id             {@link UUID}
     * @param catalogRequest {@link CatalogRequest}
     * @return Catalog
     */
    Catalog update(UUID id, CatalogRequest catalogRequest);

    /**
     * Delete catalog by id
     *
     * @param id {@link UUID}
     */
    void delete(UUID id);

    /**
     * Delete catalogs by list id
     *
     * @param ids List {@link UUID}
     */
    void deleteAll(List<UUID> ids);
}
