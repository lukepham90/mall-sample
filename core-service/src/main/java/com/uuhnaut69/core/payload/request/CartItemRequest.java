package com.uuhnaut69.core.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    @Min(0)
    @NotBlank
    private Integer quantity;

    @NotBlank
    private UUID productId;
}
