package com.uuhnaut69.mall.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ShippingResponse {

    private UUID id;

    private String shippingCountry;

    private String shippingCity;

    private String shippingAddress;

    private Integer shippingPostcode;
}
