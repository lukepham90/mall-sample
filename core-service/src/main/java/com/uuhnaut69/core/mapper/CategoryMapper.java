package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.payload.request.CategoryRequest;
import com.uuhnaut69.core.payload.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface CategoryMapper {

    void toCategoryEntity(CategoryRequest categoryRequest, @MappingTarget Category category);

    CategoryResponse toCategoryResponse(Category category);
}
