package com.bkolomiets.www.category.repository;

import com.bkolomiets.www.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(final String category);
}
