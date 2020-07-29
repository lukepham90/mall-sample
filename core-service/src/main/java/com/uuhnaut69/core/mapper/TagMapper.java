package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.payload.request.TagRequest;
import com.uuhnaut69.core.payload.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface TagMapper {

    void toTagEntity(TagRequest tagRequest, @MappingTarget Tag tag);

    TagResponse toTagResponse(Tag tag);

    List<TagResponse> toListTagResponse(List<Tag> tags);
}
