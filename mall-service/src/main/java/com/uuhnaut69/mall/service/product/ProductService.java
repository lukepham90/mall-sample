package com.uuhnaut69.mall.service.product;

import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductService {

    /**
     * Find page products
     *
     * @param pageable {@link Pageable}
     * @return page product
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * Find page products by brand id
     *
     * @param pageable {@link Pageable}
     * @param brandId  {@link UUID}
     * @return page product
     */
    Page<Product> findAllByBrandId(Pageable pageable, UUID brandId);

    /**
     * Find page products by catalog id
     *
     * @param pageable  {@link Pageable}
     * @param catalogId {@link UUID}
     * @return page product
     */
    Page<Product> findAllByCatalogId(Pageable pageable, UUID catalogId);

    /**
     * Get detail of product
     *
     * @param id {@link UUID}
     * @return Product
     */
    Product findById(UUID id);

    /**
     * Create a product
     *
     * @param productRequest {@link ProductRequest}
     * @return Product
     */
    Product create(ProductRequest productRequest);

    /**
     * Update product
     *
     * @param id             {@link UUID}
     * @param productRequest {@link ProductRequest}
     * @return Product
     */
    Product update(UUID id, ProductRequest productRequest);

    /**
     * Delete product
     *
     * @param id {@link UUID}
     */
    void delete(UUID id);

    /**
     * Delete products by given list ids
     *
     * @param ids List {@link UUID}
     */
    void deleteAll(List<UUID> ids);

}
