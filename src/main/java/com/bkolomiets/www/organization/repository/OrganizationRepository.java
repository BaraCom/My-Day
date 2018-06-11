package com.bkolomiets.www.organization.repository;

import com.bkolomiets.www.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByOrganizationName(final String name);

    Organization findByLoginAndPassword(final String login, final String password);
}
