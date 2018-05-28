package com.bkolomiets.www.organization.service;

import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.OrganizationRepository;
import com.bkolomiets.www.products.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public void add(final String organizationName, final String productName, final String login, final String password, final String mail
            , final Long phone, final String description, final String role) {
        Organization organization;
        Product product = createClearProductByOrganization(productName);

        if (isExistOrganizationName(organizationName)) {
            organizationRepository.findByOrganizationName(organizationName);

            return;
        } else {
            organization = new Organization();
        }

        organization.setOrganizationName(organizationName);
        organization.setProductName(productName);
        organization.setLogin(login);
        organization.setPassword(password);
        organization.setMail(mail);
        organization.setPhone(phone);
        organization.setDescription(description);
        organization.setRole(role);
        organization.getProductList().add(product);

        organizationRepository.save(organization);
    }

    public List<Organization> getOrganizationList() {
        return organizationRepository.findAll();
    }

    public Organization findByPassword(final String password) {
        return organizationRepository.findByPassword(password);
    }

    private boolean isExistOrganizationName(final String name) {
        return organizationRepository.findAll()
                .stream()
                .anyMatch(x -> x.getOrganizationName().equalsIgnoreCase(name));
    }

    private Product createClearProductByOrganization(final String productName) {
        return new Product(productName, 0D, 0D, 0D, " - ");
    }
}
