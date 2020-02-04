package com.uuhnaut69.mall.product.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Date;
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
@ApiModel(value = "Verify Token Model", description = "Verify Token For Active User Account")
public class VerifyToken extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "VerifyToken's id")
    private UUID id;

    @ApiModelProperty(value = "Token")
    private String token;

    @ApiModelProperty(value = "User's id")
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ApiModelProperty(value = "VerifyToken's date expired")
    private Date expiryDate;

}
