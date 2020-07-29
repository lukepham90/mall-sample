package com.uuhnaut69.core.repository.coupon;

import com.uuhnaut69.core.domain.model.Coupon;
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
public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    Page<Coupon> findAll(Pageable pageable);

    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

    void deleteByIdIn(List<UUID> uuidList);
}
