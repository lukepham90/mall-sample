package com.uuhnaut69.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uuhnaut69.core.domain.enums.CouponStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CouponResponse {

  private UUID id;

  private String code;

  private CouponStatus couponStatus;

  private Double discountPercent;

  private Timestamp startingDate;

  private Timestamp endingDate;
}
