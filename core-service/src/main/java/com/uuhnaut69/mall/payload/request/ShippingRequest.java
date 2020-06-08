package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class ShippingRequest {
    @NotBlank
    private String shippingCountry;

    @NotBlank
    private String shippingCity;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    private Integer shippingPostcode;
}
