package com.uuhnaut69.mall.repository.brand;

import com.uuhnaut69.mall.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

    Page<Brand> findAll(Pageable pageable);

    boolean existsByBrandName(String brandName);

    List<Brand> findByIdIn(List<UUID> ids);

}
