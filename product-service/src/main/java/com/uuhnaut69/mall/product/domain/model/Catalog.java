package com.uuhnaut69.mall.product.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Catalog Model", description = "Catalog's info")
public class Catalog extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Catalog's id")
    private UUID id;

    @ApiModelProperty(value = "Catalog's name")
    @Column(nullable = false, unique = true)
    private String catalogName;
}