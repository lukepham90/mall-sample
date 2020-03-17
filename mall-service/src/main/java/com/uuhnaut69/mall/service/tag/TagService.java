package com.uuhnaut69.mall.service.tag;

import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.payload.request.TagRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TagService {
    /**
     * Find tag page
     *
     * @param pageable
     * @return Page {@link Tag}
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * Find by id
     *
     * @param id
     * @return Tag
     */
    Tag findById(UUID id);

    /**
     * Create a new tag
     *
     * @param tagRequest
     * @return Tag
     */
    Tag create(TagRequest tagRequest);

    /**
     * Update a tag
     *
     * @param id
     * @param tagRequest
     * @return Tag
     */
    Tag update(UUID id, TagRequest tagRequest);

    /**
     * Delete a tag
     *
     * @param id
     */
    void delete(UUID id);

    /**
     * Get list tag that user wanna see
     *
     * @param uuids
     * @return Set tag
     */
    Set<Tag> findListTagInListIds(Set<UUID> uuids);
}
