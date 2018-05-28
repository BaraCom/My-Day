package com.bkolomiets.www.core.admin;

import com.bkolomiets.www.core.super_admin.service.SuperAdminService;
import com.bkolomiets.www.core.user_role.Role;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.OrganizationRepository;
import com.bkolomiets.www.organization.service.OrganizationService;
import com.bkolomiets.www.products.domain.Product;
import com.bkolomiets.www.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
public class AdminController {
    private final OrganizationService organizationService;
    private final ProductService productService;

    @GetMapping
    public String admin(final Model model) {

        model.addAttribute("navBarItems", SuperAdminService.getNavMenuItem());

        return "admin";
    }

    @GetMapping("/add_product")
    public String addProduct() {


        return "admin";
    }
}
