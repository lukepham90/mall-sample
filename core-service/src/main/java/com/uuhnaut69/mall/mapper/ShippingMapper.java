package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.Shipping;
import com.uuhnaut69.mall.payload.request.ShippingRequest;
import org.mapstruct.Mapper;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface ShippingMapper {

    Shipping toShippingEntity(ShippingRequest shippingRequest);
}
