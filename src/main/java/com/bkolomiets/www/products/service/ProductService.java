package com.bkolomiets.www.products.service;

import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.OrganizationRepository;
import com.bkolomiets.www.products.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final OrganizationRepository organizationRepository;

    public Set<Product> getProductByOrganizationName(final String organizationName) {
        Organization organization = organizationRepository.findByOrganizationName(organizationName);
        return organization.getProductList();
    }
}