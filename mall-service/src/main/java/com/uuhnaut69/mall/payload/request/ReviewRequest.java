package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class ReviewRequest {

    @NotBlank
    private String reviewContent;

    private UUID parentReviewId;
}
