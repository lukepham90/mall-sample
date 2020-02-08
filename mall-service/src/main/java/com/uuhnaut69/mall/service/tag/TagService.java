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
     * @throws Exception
     */
    Tag findById(UUID id) throws Exception;

    /**
     * Create a new tag
     *
     * @param tagRequest
     * @return Tag
     * @throws Exception
     */
    Tag create(TagRequest tagRequest) throws Exception;

    /**
     * Update a tag
     *
     * @param id
     * @param tagRequest
     * @return Tag
     * @throws Exception
     */
    Tag update(UUID id, TagRequest tagRequest) throws Exception;

    /**
     * Delete a tag
     *
     * @param id
     * @throws Exception
     */
    void delete(UUID id) throws Exception;

    /**
     * Get list tag that user wanna see
     *
     * @param uuids
     * @return Set tag
     * @throws Exception
     */
    Set<Tag> findListTagInListIds(Set<UUID> uuids) throws Exception;
}
