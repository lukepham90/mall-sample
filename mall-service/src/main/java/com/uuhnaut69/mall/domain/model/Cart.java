package com.uuhnaut69.mall.domain.model;

import com.uuhnaut69.mall.core.model.AbstractEntity;
import com.uuhnaut69.mall.domain.enums.Currency;
import com.uuhnaut69.mall.domain.enums.PaymentMethod;
import com.uuhnaut69.mall.domain.enums.PaymentStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Cart Model", description = "Cart's info")
public class Cart extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Cart's id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Cart's payment method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Cart's payment status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Cart's currency")
    private Currency currency;

    @ApiModelProperty(value = "Cart's original total price")
    private BigDecimal originalTotalPrice;

    @ApiModelProperty(value = "Tax")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "Cart's price that user have to pay")
    private BigDecimal priceToPay;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Set<CartItem> orderItems = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id", nullable = false)
    private Shipping shipping;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id", nullable = false)
    private Billing billing;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
