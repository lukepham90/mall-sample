package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.domain.model.Category;
import com.uuhnaut69.mall.mapper.CategoryMapper;
import com.uuhnaut69.mall.payload.request.CategoryRequest;
import com.uuhnaut69.mall.payload.response.CategoryResponse;
import com.uuhnaut69.mall.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
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
        log.debug("Insert new category {} into database", categoryRequest);
        Category category = categoryService.create(categoryRequest);
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
        return GenericResponse.builder().data(categoryResponse).build();
    }

}
