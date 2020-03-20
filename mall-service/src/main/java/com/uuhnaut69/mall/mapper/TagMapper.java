package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.payload.request.TagRequest;
import com.uuhnaut69.mall.payload.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface TagMapper {

    /**
     * Mapper code generator from tag request to tag entity
     *
     * @param tagRequest {@link TagRequest}
     * @param tag        {@link Tag}
     */
    void toTagEntity(TagRequest tagRequest, @MappingTarget Tag tag);

    /**
     * Mapper code generator from tag entity to tag response
     *
     * @param tag {@link Tag}
     * @return TagResponse
     */
    TagResponse toTagResponse(Tag tag);

    /**
     * Mapper code generator from list tag entity to list tag response
     *
     * @param tags List {@link Tag}
     * @return List TagResponse
     */
    List<TagResponse> toListTagResponse(List<Tag> tags);
}
