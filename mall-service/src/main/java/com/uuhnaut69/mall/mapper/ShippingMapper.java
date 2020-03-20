package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Shipping;
import com.uuhnaut69.mall.payload.request.ShippingRequest;
import org.mapstruct.Mapper;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface ShippingMapper {

    /**
     * Mapper code generator from shipping request to shipping entity
     *
     * @param shippingRequest {@link ShippingRequest}
     * @return Shipping
     */
    Shipping toShippingEntity(ShippingRequest shippingRequest);
}
