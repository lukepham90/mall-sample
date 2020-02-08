package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.payload.request.BrandRequest;
import com.uuhnaut69.mall.payload.response.BrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface BrandMapper {

    /**
     * Mapper code generator from brand request to brand entity
     *
     * @param brandRequest
     * @param id
     * @return Brand
     */
    @Mapping(source = "id", target = "id")
    Brand toBrandEntity(BrandRequest brandRequest, UUID id);

    /**
     * Mapper code generator from brand entity to brand response
     *
     * @param brand
     * @return BrandResponse
     */
    BrandResponse toBrandResponse(Brand brand);

    /**
     * Mapper code generator from list brand entity to list brand response
     *
     * @param brands
     * @return List BrandResponse
     */
    List<BrandResponse> toListBrandResponse(List<Brand> brands);

}
