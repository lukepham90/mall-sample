package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class UserBaseContent {

    private Set<UUID> uuidTags;
}