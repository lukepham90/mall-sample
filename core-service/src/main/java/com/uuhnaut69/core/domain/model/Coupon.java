package com.uuhnaut69.core.domain.model;

import com.uuhnaut69.common.model.AbstractEntity;
import com.uuhnaut69.core.domain.enums.CouponStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Coupon extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @Column(nullable = false)
    private Integer discountPercent;

    private Timestamp startingDate;

    private Timestamp endingDate;
}
