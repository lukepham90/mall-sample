package com.uuhnaut69.core.payload.response;

import lombok.Data;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CategoryResponse {

  private UUID id;

  private String categoryName;
}
