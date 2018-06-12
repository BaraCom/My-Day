package com.bkolomiets.www.products.repository;

import com.bkolomiets.www.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(final String name);
}
