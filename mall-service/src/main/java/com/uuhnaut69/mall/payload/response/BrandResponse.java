package com.uuhnaut69.mall.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BrandResponse {

    private UUID id;

    private String brandName;

    private String logo;
}
