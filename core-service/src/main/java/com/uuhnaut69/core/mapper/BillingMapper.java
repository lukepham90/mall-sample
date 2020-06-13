package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.Billing;
import com.uuhnaut69.core.payload.request.BillingRequest;
import org.mapstruct.Mapper;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface BillingMapper {

    Billing toBillingEntity(BillingRequest billingRequest);

}
