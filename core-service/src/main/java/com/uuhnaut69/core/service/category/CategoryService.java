package com.uuhnaut69.core.service.category;

import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.payload.request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    Category findById(UUID id);

    Category create(CategoryRequest categoryRequest);

    Category update(UUID id, CategoryRequest categoryRequest);

    void delete(UUID id);

    Set<Category> findByIdIn(Set<UUID> uuidSet);
}
