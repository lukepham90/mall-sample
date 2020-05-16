package com.uuhnaut69.mall.service.cart.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.enums.CouponStatus;
import com.uuhnaut69.mall.domain.enums.PaymentStatus;
import com.uuhnaut69.mall.domain.model.*;
import com.uuhnaut69.mall.mapper.BillingMapper;
import com.uuhnaut69.mall.mapper.CartMapper;
import com.uuhnaut69.mall.mapper.ShippingMapper;
import com.uuhnaut69.mall.payload.request.CartRequest;
import com.uuhnaut69.mall.repository.cart.CartRepository;
import com.uuhnaut69.mall.service.cart.CartService;
import com.uuhnaut69.mall.service.coupon.CouponService;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;
    private final CartMapper cartMapper;
    private final BillingMapper billingMapper;
    private final ShippingMapper shippingMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Cart> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cart> findAllByUserId(Pageable pageable, UUID userId) {
        return cartRepository.findAllByUserId(pageable, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findById(UUID id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new NotFoundException(MessageConstant.CART_NOT_FOUND));
    }

    @Override
    public Cart create(CartRequest cartRequest, UUID userId) {
        return save(cartRequest, new Cart(), userId);
    }

    @Override
    public void delete(UUID id, UUID userId) {
        Cart cart = findByIdAndUserId(id, userId);
        checkIsSucceed(cart);
        cartRepository.delete(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findByIdAndUserId(UUID id, UUID userId) {
        Optional<Cart> cart = cartRepository.findByIdAndUserId(id, userId);
        return cart.orElseThrow(() -> new NotFoundException(MessageConstant.CART_NOT_FOUND));
    }

    private Cart save(CartRequest cartRequest, Cart cart, UUID userId) {
        Set<CartItem> cartItems = new HashSet<>();
        cartMapper.toCartEntity(cartRequest, cart);
        cartRequest.getOrderItems().forEach(item -> {
            try {
                Product product = productService.findById(item.getProductId());
                cartItems.add(new CartItem(item.getQuantity(), product));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        cart.setOrderItems(cartItems);
        cart.setUser(userService.findById(userId));
        cart.setCoupon(couponService.findByCode(cartRequest.getCouponCode()));
        calculatePrice(cartRequest, cart);
        Billing billing = billingMapper.toBillingEntity(cartRequest.getBillingRequest());
        cart.setBilling(billing);
        Shipping shipping = shippingMapper.toShippingEntity(cartRequest.getShippingRequest());
        cart.setShipping(shipping);
        return cartRepository.save(cart);
    }

    private void calculatePrice(CartRequest cartRequest, Cart cart) {
        List<Integer> listPrice = new ArrayList<>();

        cartRequest.getOrderItems().stream().filter(e -> !e.getQuantity().equals(0)).forEach(e -> {
            try {
                Product product = productService.findById(e.getProductId());
                listPrice.add(e.getQuantity() * product.getOriginalPrice().intValue());
            } catch (Exception e1) {
                log.error(e1.getMessage());
            }
        });
        int originalTotalPrice = listPrice.stream().reduce(0, Integer::sum);
        cart.setOriginalTotalPrice(new BigDecimal(originalTotalPrice));

        if (cartRequest.getCouponCode() != null) {
            Coupon coupon = couponService.findByCode(cartRequest.getCouponCode());
            if (coupon.getCouponStatus().name().equals(CouponStatus.VALID.name())) {
                int discountPrice = originalTotalPrice * coupon.getDiscountPercent() / 100;
                int priceToPay = originalTotalPrice - discountPrice;
                calculateTaxPrice(cart, priceToPay);
            }
        } else {
            calculateTaxPrice(cart, originalTotalPrice);
        }

    }

    private void calculateTaxPrice(Cart cart, int priceToPay) {
        int tax = priceToPay * 8 / 100;
        cart.setTaxPrice(new BigDecimal(tax));
        int finalPrice = priceToPay + tax;
        cart.setPriceToPay(new BigDecimal(finalPrice));
    }

    private void checkIsSucceed(Cart cart) {
        if (cart.getPaymentStatus().name().equals(PaymentStatus.SUCCEED.name())) {
            throw new BadRequestException(MessageConstant.PAYMENT_SUCCEED_CANT_BE_DELETE);
        }
    }
}
