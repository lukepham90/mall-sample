package com.uuhnaut69.mall.domain.idclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RatingProductKey implements Serializable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "user_id")
    private UUID userId;

}
