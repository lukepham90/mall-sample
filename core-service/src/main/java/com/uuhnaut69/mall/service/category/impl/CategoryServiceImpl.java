package com.uuhnaut69.mall.service.category.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Category;
import com.uuhnaut69.mall.mapper.CategoryMapper;
import com.uuhnaut69.mall.payload.request.CategoryRequest;
import com.uuhnaut69.mall.repository.category.CategoryRepository;
import com.uuhnaut69.mall.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
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
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category create(CategoryRequest categoryRequest) {
        return save(categoryRequest, new Category());
    }

    @Override
    public Category update(UUID id, CategoryRequest categoryRequest) {
        Category category = findById(id);
        return save(categoryRequest, category);
    }

    @Override
    public void delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Category> findByIdIn(Set<UUID> uuidSet) {
        return categoryRepository.findByIdIn(uuidSet);
    }

    private Category save(CategoryRequest categoryRequest, Category category) {
        categoryMapper.toCategoryEntity(categoryRequest, category);
        return categoryRepository.save(category);
    }
}
