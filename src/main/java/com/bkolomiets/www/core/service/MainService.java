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
            return getAnonymousNavBar();
        }
    }

    public static boolean getLogButtonByRole() {
        return isSameRoleName(Role.USER) || isSameRoleName(Role.ADMIN) || isSameRoleName(Role.SUPER_ADMIN);
    }

    public static boolean isSameRoleName(final Role role) {
        return getRoles().stream().anyMatch(a -> a.getAuthority().equals(role.name()));
    }

    private static Map<String, String> getUserNavBar() {
        Map<String,String> userItems = new LinkedHashMap<>();
        userItems.put("/", "Home");
        userItems.put("/about", "About");
        userItems.put("/user/my_purchases", "My purchases");

        return userItems;
    }

    private static Map<String, String> getAdminNavBar() {
        Map<String, String> adminItems = new LinkedHashMap<>();
        adminItems.put("/", "Home");
        adminItems.put("/admin/about", "About");
        adminItems.put("/admin/add_product", "Add product");
        adminItems.put("/admin/all_products", "All products");
        adminItems.put("/admin/update_product", "Update products list");
        adminItems.put("/admin/delete_product", "Delete product");

        return adminItems;
    }

    private static Map<String, String> getSuperAdminNavBar() {
        Map<String, String> superAdminItems = new LinkedHashMap<>();
        superAdminItems.put("/", "Home");
        superAdminItems.put("/super_admin/add_organization", "Add organization");
        superAdminItems.put("/super_admin/all_organization", "All organizations");
        superAdminItems.put("/super_admin/update_organization", "Update organization");
        superAdminItems.put("/super_admin/delete_organization", "Delete organization");
        superAdminItems.put("/super_admin/all_products", "All products");
        superAdminItems.put("/super_admin/add_category", "Add category");
        superAdminItems.put("/super_admin/all_categories", "All categories");

        return superAdminItems;
    }

    private static Map<String, String> getAnonymousNavBar() {
        Map<String, String> anonymousItems = new LinkedHashMap<>();
        anonymousItems.put("/", "Home");
        anonymousItems.put("/about", "About");

        return anonymousItems;
    }

    private static Collection<? extends GrantedAuthority> getRoles() {
        return getContext().getAuthentication().getAuthorities();
    }
}