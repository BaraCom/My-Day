package com.bkolomiets.www.core.super_admin.controller;

import com.bkolomiets.www.core.super_admin.service.SuperAdminService;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.service.OrganizationService;
import com.bkolomiets.www.products.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Set;

import static com.bkolomiets.www.core.super_admin.service.SuperAdminService.getAppRoles;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/super_admin")
public class SuperAdminController {
    private final OrganizationService organizationService;

    @GetMapping
    public String superAdmin(final Model model) {
        List<Organization> organizationList = organizationService.getOrganizationList();
        int quantityOrganization = organizationList.size();

        model.addAttribute("quantityOrganization", quantityOrganization);
        model.addAttribute("navBarItems", SuperAdminService.getNavMenuItem());

        return "super_admin";
    }

    @GetMapping("/add")
    public String add(final Model model) {
        organizationService.add("Waterok", "water", "loginooo", "Pas897ord", "e-mail"
                , 380992223311L
                , "Some description for this organization!!!"
                , "ADMIN");

        List<Organization> organizationList = organizationService.getOrganizationList();
        int quantityOrganization = organizationList.size();

        model.addAttribute("quantityOrganization", quantityOrganization);
        model.addAttribute("navBarItems", SuperAdminService.getNavMenuItem());
        model.addAttribute("rolesList", getAppRoles());

        return "add_organization";
    }

    @PostMapping("/add")
    public String addOrganization(@RequestParam("organizationName") final String organizationName
                                , @RequestParam("productName") final String productName
                                , @RequestParam("login") final String login
                                , @RequestParam("password") final String password
                                , @RequestParam("mail") final String mail
                                , @RequestParam("phone") final Long phone
                                , @RequestParam("description") final String description
                                , @RequestParam("role") final String role) {
        organizationService.add(organizationName, productName, login, password, mail, phone, description, role);

        return "redirect:/super_admin/all_organization";
    }

    @GetMapping("/all_organization")
    public String all(final Model model) {
        List<Organization> organizationList = organizationService.getOrganizationList();

        int quantityOrganization = organizationList.size();

        model.addAttribute("organizations", organizationList);
        model.addAttribute("quantityOrganization", quantityOrganization);
        model.addAttribute("navBarItems", SuperAdminService.getNavMenuItem());

        return "/all_organization";
    }

    @PostMapping("/all_organization")
    public String getProductsNames(@RequestParam("organizationName") final String organizationName, final Model model) {
        Set<Product> productNameList = organizationService.getProductNameList(organizationName);

        model.addAttribute("productsList", productNameList);

        return "redirect:/all_products";
    }
}