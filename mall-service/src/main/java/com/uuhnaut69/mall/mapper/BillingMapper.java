package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.Billing;
import com.uuhnaut69.mall.payload.request.BillingRequest;
import org.mapstruct.Mapper;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface BillingMapper {

    /**
     * Mapper code generator from billing request to billing entity
     *
     * @param billingRequest {@link BillingRequest}
     * @return Billing
     */
    Billing toBillingEntity(BillingRequest billingRequest);

}
