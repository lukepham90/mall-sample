package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
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
@Mapper(config = SpringMapstructConfig.class)
public interface TagMapper {

    void toTagEntity(TagRequest tagRequest, @MappingTarget Tag tag);

    TagResponse toTagResponse(Tag tag);

    List<TagResponse> toListTagResponse(List<Tag> tags);
}
