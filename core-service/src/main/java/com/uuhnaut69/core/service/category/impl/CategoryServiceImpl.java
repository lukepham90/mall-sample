package com.uuhnaut69.core.service.category.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.mapper.CategoryMapper;
import com.uuhnaut69.core.payload.request.CategoryRequest;
import com.uuhnaut69.core.repository.category.CategoryRepository;
import com.uuhnaut69.core.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get categories");
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(UUID id) {
        log.debug("Request to get category by id {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category create(CategoryRequest categoryRequest) {
        log.debug("Request to create category {}", categoryRequest);
        return save(categoryRequest, new Category());
    }

    @Override
    public Category update(UUID id, CategoryRequest categoryRequest) {
        log.debug("Request to update category's id {} with data {}", id, categoryRequest);
        Category category = findById(id);
        return save(categoryRequest, category);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete category's id {}", id);
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Category> findByIdIn(Set<UUID> uuidSet) {
        log.debug("Request to get category has id in {}", uuidSet);
        return categoryRepository.findByIdIn(uuidSet);
    }

    private Category save(CategoryRequest categoryRequest, Category category) {
        categoryMapper.toCategoryEntity(categoryRequest, category);
        return categoryRepository.save(category);
    }
}
