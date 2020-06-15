package com.uuhnaut69.core.service.tag;

import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.payload.request.TagRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TagService {

  Page<Tag> findAll(Pageable pageable);

  Tag findById(UUID id);

  Tag create(TagRequest tagRequest);

  Tag update(UUID id, TagRequest tagRequest);

  void delete(UUID id);

  Set<Tag> findListTagInListIds(Set<UUID> uuids);
}
