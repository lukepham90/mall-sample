package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
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
@Mapper(config = SpringMapstructConfig.class)
public interface ProductMapper {

    /**
     * Mapper code generator from product request to product entity
     *
     * @param productRequest {@link ProductRequest}
     * @param product        {@link Product}
     */
    void toProductEntity(ProductRequest productRequest, @MappingTarget Product product);

    /**
     * Mapper code generator from product entity to product response
     *
     * @param product {@link Product}
     * @return ProductResponse
     */
    ProductResponse toProductResponse(Product product);

    /**
     * Mapper code generator from list product entity to list product response
     *
     * @param products List {@link Product}
     * @return List ProductResponse
     */
    List<ProductResponse> toListProductResponse(List<Product> products);

    /**
     * Mapper code generator from set tag entity to set tag response
     *
     * @param tags Set {@link Tag}
     * @return Set TagResponse
     */
    Set<TagResponse> toSetTagResponse(Set<Tag> tags);

}
