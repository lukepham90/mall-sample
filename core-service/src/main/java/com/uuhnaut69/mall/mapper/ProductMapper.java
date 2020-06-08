package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.Category;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.payload.request.ProductRequest;
import com.uuhnaut69.mall.payload.response.CategoryResponse;
import com.uuhnaut69.mall.payload.response.ProductResponse;
import com.uuhnaut69.mall.payload.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface ProductMapper {

    void toProductEntity(ProductRequest productRequest, @MappingTarget Product product);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toListProductResponse(List<Product> products);

    Set<TagResponse> toSetTagResponse(Set<Tag> tags);

    Set<CategoryResponse> toSetCategoryResponse(Set<Category> categories);
}
