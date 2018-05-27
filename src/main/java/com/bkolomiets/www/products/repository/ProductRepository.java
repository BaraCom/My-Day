package com.bkolomiets.www.products.repository;

import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT p from Product p where lower(p.productName) = lower(:name)")
    Product findByOrganizationName(/*@Param("name")*/ final String name);
}
