package com.bkolomiets.www.organization.service;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.IOrganizationRepository;
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
    private final IOrganizationRepository organizationRepository;
    private final IUserRepository userRepository;

    public void add(final String organizationName
                  , final String login
                  , final String password
                  , final String mail
                  , final Long phone
                  , final String description
                  , final String role) {
        Organization organization;

        if (isExistOrganizationName(organizationName)) {
            organizationRepository.findByOrganizationName(organizationName);

            return;
        } else {
            organization = new Organization();
        }

        organization.setOrganizationName(organizationName);
        organization.setLogin(login);
        organization.setPassword(password);
        organization.setMail(mail);
        organization.setPhone(phone);
        organization.setDescription(description);
        organization.setRole(role);

        organizationRepository.save(organization);
    }

    public List<Organization> getOrganizationList() {
        return organizationRepository.findAll();
    }

    public Organization getOrganizationByUserName(final String userName) {
        User user = userRepository.findByUsername(userName);

        return organizationRepository.findByLoginAndPassword(userName, user.getPassword());
    }

    private boolean isExistOrganizationName(final String name) {
        return organizationRepository.findAll()
                .stream()
                .anyMatch(x -> x.getOrganizationName().equalsIgnoreCase(name));
    }
}