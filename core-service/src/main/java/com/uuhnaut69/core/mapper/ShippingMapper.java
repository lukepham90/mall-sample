package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.Shipping;
import com.uuhnaut69.core.payload.request.ShippingRequest;
import org.mapstruct.Mapper;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface ShippingMapper {

  Shipping toShippingEntity(ShippingRequest shippingRequest);
}
