package com.uuhnaut69.mall.domain.model;

import com.uuhnaut69.mall.core.model.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ApiModel(value = "Brand Model", description = "Brand info")
public class Brand extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "Brand's id")
    private UUID id;

    @ApiModelProperty(value = "Brand's name")
    @Column(nullable = false, unique = true)
    private String brandName;

    @ApiModelProperty(value = "Brand's logo")
    private String logo;

    public Brand(String brandName, String logo) {
        this.brandName = brandName;
        this.logo = logo;
    }

}
