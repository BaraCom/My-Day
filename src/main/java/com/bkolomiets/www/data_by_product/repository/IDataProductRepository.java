package com.bkolomiets.www.data_by_product.repository;

import com.bkolomiets.www.data_by_product.domain.DataProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDataProductRepository extends JpaRepository<DataProduct, Long> {

}
