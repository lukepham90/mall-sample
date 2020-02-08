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
     * @param pageable
     * @return page product
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * Find page products by brand id
     *
     * @param pageable
     * @param brandId
     * @return page product
     */
    Page<Product> findAllByBrandId(Pageable pageable, UUID brandId);

    /**
     * Find page products by catalog id
     *
     * @param pageable
     * @param catalogId
     * @return page product
     */
    Page<Product> findAllByCatalogId(Pageable pageable, UUID catalogId);

    /**
     * Get detail of product
     *
     * @param id
     * @return Product
     * @throws Exception
     */
    Product findById(UUID id) throws Exception;

    /**
     * Create a product
     *
     * @param productRequest
     * @return Product
     * @throws Exception
     */
    Product create(ProductRequest productRequest) throws Exception;

    /**
     * Update product
     *
     * @param id
     * @param productRequest
     * @return Product
     * @throws Exception
     */
    Product update(UUID id, ProductRequest productRequest) throws Exception;

    /**
     * Delete product
     *
     * @param id
     * @throws Exception
     */
    void delete(UUID id) throws Exception;

    /**
     * Delete products by given list ids
     *
     * @param ids
     * @throws Exception
     */
    void deleteAll(List<UUID> ids) throws Exception;

}
