package com.uuhnaut69.core.service.cart;

import com.uuhnaut69.core.domain.model.Cart;
import com.uuhnaut69.core.payload.request.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CartService {

    Page<Cart> findAll(Pageable pageable);

    Page<Cart> findAllByUserId(Pageable pageable, UUID userId);

    Cart findById(UUID id);

    Cart findByIdAndUserId(UUID id, UUID userId);

    Cart create(CartRequest cartRequest, UUID userId);

    void delete(UUID id, UUID userId);
}
