package com.uuhnaut69.mall.service.tag.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.mapper.TagMapper;
import com.uuhnaut69.mall.payload.request.TagRequest;
import com.uuhnaut69.mall.repository.tag.TagRepository;
import com.uuhnaut69.mall.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag findById(UUID id) throws Exception {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElseThrow(() -> new NotFoundException(MessageConstant.TAG_NOT_FOUND));
    }

    @Override
    public Tag create(TagRequest tagRequest) throws Exception {
        checkTagNameValid(tagRequest.getTagName());
        return save(tagRequest, new Tag());
    }

    @Override
    public Tag update(UUID id, TagRequest tagRequest) throws Exception {
        Tag tag = findById(id);
        if (!tag.getTagName().equals(tagRequest.getTagName())) {
            checkTagNameValid(tagRequest.getTagName());
        }
        return save(tagRequest, tag);
    }

    @Override
    public void delete(UUID id) throws Exception {
        Tag tag = findById(id);
        tagRepository.delete(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Tag> findListTagInListIds(Set<UUID> uuids) throws Exception {
        return tagRepository.findByIdIn(uuids);
    }

    /**
     * @param tagRequest
     * @param tag
     * @return Tag
     */
    private Tag save(TagRequest tagRequest, Tag tag) {
        tagMapper.toTagEntity(tagRequest, tag);
        return tagRepository.save(tag);
    }

    /**
     * Check tag name valid or not
     *
     * @param tagName
     * @throws Exception
     */
    private void checkTagNameValid(String tagName) throws Exception {
        if (tagRepository.existsByTagName(tagName)) {
            throw new BadRequestException(MessageConstant.TAG_ALREADY_EXIST);
        }
    }
}
