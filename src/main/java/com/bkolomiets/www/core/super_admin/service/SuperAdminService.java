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

    public static List<String> getAppRoles() {
        List<String> rolesList = new ArrayList<>();
        for (Role role : Role.values()) {
            if (!role.name().equalsIgnoreCase(Role.SUPER_ADMIN.name())) {
                rolesList.add(role.name());
            }
        }

        return rolesList;
    }
}
