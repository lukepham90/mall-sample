package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.payload.request.ProductRequest;
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
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Mapper code generator from product request to product entity
     *
     * @param productRequest
     * @param product
     */
    void toProductEntity(ProductRequest productRequest, @MappingTarget Product product);

    /**
     * Mapper code generator from product entity to product response
     *
     * @param product
     * @return ProductResponse
     */
    ProductResponse toProductResponse(Product product);

    /**
     * Mapper code generator from list product entity to list product response
     *
     * @param products
     * @return List ProductResponse
     */
    List<ProductResponse> toListProductResponse(List<Product> products);

    /**
     * Mapper code generator from set tag entity to set tag response
     *
     * @param tags
     * @return Set TagResponse
     */
    Set<TagResponse> toSetTagResponse(Set<Tag> tags);

}
