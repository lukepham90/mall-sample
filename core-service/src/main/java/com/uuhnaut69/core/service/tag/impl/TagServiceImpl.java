package com.uuhnaut69.core.service.tag.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.mapper.TagMapper;
import com.uuhnaut69.core.payload.request.TagRequest;
import com.uuhnaut69.core.repository.tag.TagRepository;
import com.uuhnaut69.core.service.tag.TagService;
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
    public Tag findById(UUID id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElseThrow(() -> new NotFoundException(MessageConstant.TAG_NOT_FOUND));
    }

    @Override
    public Tag create(TagRequest tagRequest) {
        checkTagNameValid(tagRequest.getTagName());
        return save(tagRequest, new Tag());
    }

    @Override
    public Tag update(UUID id, TagRequest tagRequest) {
        Tag tag = findById(id);
        if (!tag.getTagName().equals(tagRequest.getTagName())) {
            checkTagNameValid(tagRequest.getTagName());
        }
        return save(tagRequest, tag);
    }

    @Override
    public void delete(UUID id) {
        Tag tag = findById(id);
        tagRepository.delete(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Tag> findListTagInListIds(Set<UUID> uuids) {
        return tagRepository.findByIdIn(uuids);
    }

    private Tag save(TagRequest tagRequest, Tag tag) {
        tagMapper.toTagEntity(tagRequest, tag);
        return tagRepository.save(tag);
    }

    private void checkTagNameValid(String tagName) {
        if (tagRepository.existsByTagName(tagName)) {
            throw new BadRequestException(MessageConstant.TAG_ALREADY_EXIST);
        }
    }
}
