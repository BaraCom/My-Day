package com.bkolomiets.www.core.service;

import com.bkolomiets.www.core.user_role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * @author Broislav Kolomiets
 */
@Service
public class MainService {

    public static Map<String, String> getNavBarByRole() {
        if (isSameRoleName(Role.USER)) {
            return getUserNavBar();
        } else if (isSameRoleName(Role.ADMIN)) {
             return getAdminNavBar();
        } else if (isSameRoleName(Role.SUPER_ADMIN)) {
            return getSuperAdminNavBar();
        }else {
            return null;
        }
    }

    public static boolean getLogButtonByRole() {
        return isSameRoleName(Role.USER) || isSameRoleName(Role.ADMIN) || isSameRoleName(Role.SUPER_ADMIN);
    }

    private static Map<String, String> getUserNavBar() {
        Map<String,String> userItems = new HashMap<>();
        userItems.put("/registration", "Registration");

        return userItems;
    }

    private static Map<String, String> getAdminNavBar() {
        Map<String, String> adminItems = new HashMap<>();
        adminItems.put("/add_product", "Add product");

        return adminItems;
    }

    private static Map<String, String> getSuperAdminNavBar() {
        Map<String, String> superAdminItems = new HashMap<>();
        superAdminItems.put("/add_organization", "Add organization");

        return superAdminItems;
    }

    private static boolean isSameRoleName(final Role role) {
        return getRoles().stream().anyMatch(a -> a.getAuthority().equals(role.name()));
    }

    private static Collection<? extends GrantedAuthority> getRoles() {
        return getContext().getAuthentication().getAuthorities();
    }
}