package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.Category;
import com.uuhnaut69.mall.payload.request.CategoryRequest;
import com.uuhnaut69.mall.payload.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface CategoryMapper {

    void toCategoryEntity(CategoryRequest categoryRequest, @MappingTarget Category category);

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toListCategoryResponse(List<Category> categories);
}
