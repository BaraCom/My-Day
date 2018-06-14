package com.bkolomiets.www.category.service;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.category.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {
    private final ICategoryRepository categoryRepository;

    public void addCategory(final String category) {
        Category byCategory = categoryRepository.findByCategory(category);

        if (byCategory == null) {
            categoryRepository.save(new Category(category));
        }
    }
}
