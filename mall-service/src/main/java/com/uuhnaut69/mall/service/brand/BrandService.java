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
     * @param pageable {@link Pageable}
     * @return brand pages
     */
    Page<Brand> findAll(Pageable pageable);

    /**
     * Find brand by id
     *
     * @param id {@link UUID}
     * @return Brand
     */
    Brand findById(UUID id);

    /**
     * Create a new brand
     *
     * @param brandRequest {@link BrandRequest}
     * @return Brand
     */
    Brand create(BrandRequest brandRequest);

    /**
     * Update brand
     *
     * @param id           {@link UUID}
     * @param brandRequest {@link BrandRequest}
     * @return Brand
     */
    Brand update(UUID id, BrandRequest brandRequest);

    /**
     * Delete brand by id
     *
     * @param id {@link UUID}
     */
    void delete(UUID id);

    /**
     * Delete brands by list id
     *
     * @param idList List {@link UUID}
     */
    void deleteAll(List<UUID> idList);

}
