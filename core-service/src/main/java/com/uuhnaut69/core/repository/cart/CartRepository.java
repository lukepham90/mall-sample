package com.uuhnaut69.core.repository.cart;

import com.uuhnaut69.core.domain.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

  @EntityGraph(attributePaths = {"orderItems", "shipping", "billing", "coupon", "user"})
  Optional<Cart> findByIdAndUserId(UUID id, UUID userId);

  @EntityGraph(attributePaths = {"orderItems", "shipping", "billing", "coupon", "user"})
  Page<Cart> findAll(Pageable pageable);

  @EntityGraph(attributePaths = {"orderItems", "shipping", "billing", "coupon", "user"})
  Page<Cart> findAllByUserId(Pageable pageable, UUID userId);

  void deleteByIdAndUserId(UUID cartId, UUID userId);
}
