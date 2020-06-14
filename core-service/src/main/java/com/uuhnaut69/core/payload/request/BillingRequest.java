package com.uuhnaut69.core.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class BillingRequest {

    @NotBlank
    private String billingCountry;

    @NotBlank
    private String billingCity;

    @NotBlank
    private String billingAddress;

    @NotBlank
    private Integer billingPostcode;

}
