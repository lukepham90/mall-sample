package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.payload.request.BrandRequest;
import com.uuhnaut69.mall.payload.response.BrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface BrandMapper {

    /**
     * Mapper code generator from brand request to brand entity
     *
     * @param brandRequest {@link BrandRequest}
     * @param brand        {@link Brand}
     */
    void toBrandEntity(BrandRequest brandRequest, @MappingTarget Brand brand);

    /**
     * Mapper code generator from brand entity to brand response
     *
     * @param brand {@link Brand}
     * @return BrandResponse
     */
    BrandResponse toBrandResponse(Brand brand);

    /**
     * Mapper code generator from list brand entity to list brand response
     *
     * @param brands List {@link Brand}
     * @return List BrandResponse
     */
    List<BrandResponse> toListBrandResponse(List<Brand> brands);

}
