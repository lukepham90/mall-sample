package com.uuhnaut69.core.repository.product;

import com.uuhnaut69.core.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "select distinct product from Product product left join fetch product.categories " +
            "left join fetch product.tags", countQuery = "select count(distinct product) from Product product")
    Page<Product> findAllProducts(Pageable pageable);

    @Query(value = "select product from Product product left join fetch product.categories " +
            "left join fetch product.tags where product.id = :id")
    Optional<Product> findById(UUID id);

    void deleteByIdIn(List<UUID> uuidList);

}
