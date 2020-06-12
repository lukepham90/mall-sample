package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.mapper.CategoryMapper;
import com.uuhnaut69.core.payload.request.CategoryRequest;
import com.uuhnaut69.core.payload.response.CategoryResponse;
import com.uuhnaut69.core.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Category", value = "Category Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @ApiOperation(value = "Create Category Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CATEGORY_URL)
    public GenericResponse create(@RequestBody @Valid CategoryRequest categoryRequest) {
        Category category = categoryService.create(categoryRequest);
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
        return GenericResponse.builder().data(categoryResponse).build();
    }

}
