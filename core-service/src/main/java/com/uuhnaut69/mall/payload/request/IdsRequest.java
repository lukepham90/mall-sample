package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class IdsRequest {
    @NotNull
    private List<UUID> ids;
}