package com.bkolomiets.www.core.repository;

import com.bkolomiets.www.core.user_role.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav Kolomiets
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
}
