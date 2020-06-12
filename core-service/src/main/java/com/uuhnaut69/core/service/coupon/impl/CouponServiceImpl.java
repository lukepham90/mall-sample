package com.uuhnaut69.core.service.coupon.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.core.domain.model.Coupon;
import com.uuhnaut69.core.mapper.CouponMapper;
import com.uuhnaut69.core.payload.request.CouponRequest;
import com.uuhnaut69.core.repository.coupon.CouponRepository;
import com.uuhnaut69.core.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Coupon> findAll(Pageable pageable) {
        log.debug("Request to get coupons");
        return couponRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findByCode(String code) {
        log.debug("Request to get coupon by code {}", code);
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        return coupon.orElseThrow(() -> new NotFoundException(MessageConstant.COUPON_NOT_FOUND));
    }

    @Override
    public Coupon create(CouponRequest couponRequest) {
        log.debug("Request to create coupon {}", couponRequest);
        checkCouponCodeValid(couponRequest.getCode());
        return save(couponRequest, new Coupon());
    }

    @Override
    public Coupon update(UUID id, CouponRequest couponRequest) {
        log.debug("Request to update coupon's id {} with data {}", id, couponRequest);
        Coupon coupon = findById(id);
        if (!coupon.getCode().equals(couponRequest.getCode())) {
            checkCouponCodeValid(couponRequest.getCode());
        }
        return save(couponRequest, coupon);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete coupon by id {}", id);
        couponRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<UUID> ids) {
        log.debug("Request to delete coupon has id in list {}", ids);
        couponRepository.deleteByIdIn(ids);
    }

    private Coupon findById(UUID id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon.orElseThrow(() -> new NotFoundException(MessageConstant.COUPON_NOT_FOUND));
    }

    private Coupon save(CouponRequest couponRequest, Coupon coupon) {
        couponMapper.toCouponEntity(couponRequest, coupon);
        return couponRepository.save(coupon);
    }

    private void checkCouponCodeValid(String code) {
        if (couponRepository.existsByCode(code)) {
            throw new BadRequestException(MessageConstant.COUPON_ALREADY_EXIST);
        }
    }
}
