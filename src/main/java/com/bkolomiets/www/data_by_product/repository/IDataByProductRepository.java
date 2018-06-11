package com.bkolomiets.www.data_by_product.repository;

import com.bkolomiets.www.data_by_product.DataByProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDataByProductRepository extends JpaRepository<DataByProduct, Long> {

    DataByProduct findByProductName(final String productName);
}
