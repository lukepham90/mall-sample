package com.uuhnaut69.mall.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uuhnaut69.mall.payload.response.base.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse extends AbstractResponse {

    private UUID id;

    private String reviewContent;

    private Long replyCount;

    private UUID userId;

    private String username;

    private String userAvatar;

    private UUID parentReviewId;

}
