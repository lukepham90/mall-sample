package com.uuhnaut69.mall.repository.catalog;

import com.uuhnaut69.mall.domain.model.Catalog;
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
public interface CatalogRepository extends JpaRepository<Catalog, UUID> {

    List<Catalog> findAll();

    List<Catalog> findByIdIn(List<UUID> ids);

    Page<Catalog> findAll(Pageable pageable);

    boolean existsByCatalogName(String catalogName);

}
