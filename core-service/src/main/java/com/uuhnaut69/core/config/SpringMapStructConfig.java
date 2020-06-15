package com.uuhnaut69.core.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

/**
 * @author uuhnaut
 * @project mall
 */
@MapperConfig(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SpringMapStructConfig {}
