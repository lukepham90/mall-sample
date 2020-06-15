package com.uuhnaut69.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BillingResponse {

  private UUID id;

  private String billingCountry;

  private String billingCity;

  private String billingAddress;

  private Integer billingPostcode;
}
