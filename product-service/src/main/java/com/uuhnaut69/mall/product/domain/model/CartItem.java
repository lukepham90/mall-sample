package com.uuhnaut69.mall.product.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@ApiModel(value = "Cart's Item Model", description = "Cart item's info")
public class CartItem extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Cart item 's id")
    private UUID id;

    @ApiModelProperty(value = "Cart item 's quantity")
    private Integer quantity;

    @ApiModelProperty(value = "Product's id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public CartItem(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

}
