package com.bkolomiets.www.data_by_product.repository;

import com.bkolomiets.www.data_by_product.domain.DataProduct;
import com.bkolomiets.www.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface IDataProductRepository extends JpaRepository<DataProduct, Long> {

    DataProduct findDataProductByOrganizationAndProductName(final Organization organization, final String productName);

    Set<DataProduct> findAllByOrganization(final Organization organization);
}
