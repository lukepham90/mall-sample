package com.uuhnaut69.mall.product.domain.model;

import com.uuhnaut69.mall.product.domain.enums.CouponStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@ApiModel(value = "Coupon Model", description = "Coupon info")
public class Coupon extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Coupon's id")
    private UUID id;

    @ApiModelProperty(value = "Coupon's code")
    @Column(nullable = false, unique = true)
    private String code;

    @ApiModelProperty(value = "Coupon's status")
    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    @ApiModelProperty(value = "Coupon's discount")
    private Integer discountPercent;

    @ApiModelProperty(value = "Coupon's starting date")
    private Timestamp startingDate;

    @ApiModelProperty(value = "Coupon's ending date")
    private Timestamp endingDate;
}
