package com.uuhnaut69.mall.domain.model;

import com.uuhnaut69.mall.core.model.AbstractEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Shipping Model", description = "Shipping payment's info")
public class Shipping extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String shippingCountry;

    private String shippingCity;

    private String shippingAddress;

    private Integer shippingPostcode;
}
