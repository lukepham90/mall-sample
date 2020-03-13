package com.uuhnaut69.mall.repository.cart;

import com.uuhnaut69.mall.domain.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByIdAndUserId(UUID id, UUID userId);

    Page<Cart> findAll(Pageable pageable);

    Page<Cart> findAllByUserId(Pageable pageable, UUID userId);
}