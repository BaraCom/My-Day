package com.bkolomiets.www.data_by_product.repository;

import com.bkolomiets.www.data_by_product.domain.DataProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface IDataProductRepository extends JpaRepository<DataProduct, Long> {

    DataProduct findByProductName(final String productName);
}
