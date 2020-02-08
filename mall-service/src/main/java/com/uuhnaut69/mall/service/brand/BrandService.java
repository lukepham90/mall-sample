package com.uuhnaut69.mall.service.brand;

import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.payload.request.BrandRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface BrandService {

    /**
     * Find brand pages
     *
     * @param pageable
     * @return brand pages
     */
    Page<Brand> findAll(Pageable pageable);

    /**
     * Find brand by id
     *
     * @param id
     * @return Brand
     * @throws Exception
     */
    Brand findById(UUID id) throws Exception;

    /**
     * Create a new brand
     *
     * @param brandRequest
     * @return Brand
     * @throws Exception
     */
    Brand create(BrandRequest brandRequest) throws Exception;

    /**
     * Update brand
     *
     * @param id
     * @param brandRequest
     * @return Brand
     * @throws Exception
     */
    Brand update(UUID id, BrandRequest brandRequest) throws Exception;

    /**
     * Delete brand by id
     *
     * @param id
     * @throws Exception
     */
    void delete(UUID id) throws Exception;

    /**
     * Delete brands by list id
     *
     * @param idList
     * @throws Exception
     */
    void deleteAll(List<UUID> idList) throws Exception;

}
