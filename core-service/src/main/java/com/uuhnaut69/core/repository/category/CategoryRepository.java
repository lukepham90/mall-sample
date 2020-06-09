package com.uuhnaut69.core.repository.category;

import com.uuhnaut69.core.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByCategoryName(String categoryName);

    Set<Category> findByIdIn(Set<UUID> uuidSet);
}
