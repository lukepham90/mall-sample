package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class IdsRequest {
    private List<UUID> ids;
}