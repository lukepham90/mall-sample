package com.uuhnaut69.mall.repository.product;

import com.uuhnaut69.mall.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

    Page<Product> findAll(Pageable pageable);

    List<Product> findByIdIn(List<UUID> ids);

    Optional<Product> findById(UUID id);

}
