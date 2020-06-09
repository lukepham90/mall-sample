package com.uuhnaut69.core.payload.request;

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
