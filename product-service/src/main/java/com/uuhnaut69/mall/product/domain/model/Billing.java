package com.uuhnaut69.mall.product.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@ApiModel(value = "Billing Model", description = "Billing payment's info")
public class Billing extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Billing's id")
    private UUID id;

    @ApiModelProperty(value = "Country's name")
    private String billingCountry;

    @ApiModelProperty(value = "City's name")
    private String billingCity;

    @ApiModelProperty(value = "Billing's address")
    private String billingAddress;

    @ApiModelProperty(value = "Post code")
    private Integer billingPostcode;
}
