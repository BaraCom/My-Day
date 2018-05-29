package com.bkolomiets.www.core.super_admin.service;

import com.bkolomiets.www.core.user_role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * @author Borislav Kolomiets
 */
@Service
public class SuperAdminService {

    /*public static Map<String, String> getNavMenuItem() {
        Map<String, String> superAdminItems = new HashMap<>();
        superAdminItems.put("/super_admin/add", "Add organization");
        superAdminItems.put("/super_admin/all_organization", "All organization");

        Map<String, String> adminItems = new HashMap<>();
        adminItems.put("/add_product", "Add product");

        if (isSameRoleName(Role.USER)) {
            return adminItems;
        } else if (!isSameRoleName(Role.SUPER_ADMIN)) {
            return superAdminItems;
        } else {
            return null;
        }
    }*/

    public static List<String> getAppRoles() {
        List<String> rolesList = new ArrayList<>();
        for (Role role : Role.values()) {
            if (!role.name().equalsIgnoreCase(Role.SUPER_ADMIN.name())) {
                rolesList.add(role.name());
            }
        }

        return rolesList;
    }

//    private static boolean isSameRoleName(final Role role) {
//        return getRoles().stream().anyMatch(a -> a.getAuthority().equals(role.name()));
//    }

//    private static Collection<? extends GrantedAuthority> getRoles() {
//        return getContext().getAuthentication().getAuthorities();
//    }
}
