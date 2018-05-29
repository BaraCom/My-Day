package com.bkolomiets.www.core.super_admin.controller;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.service.MainService;
import com.bkolomiets.www.core.super_admin.service.SuperAdminService;
import com.bkolomiets.www.core.user_role.Role;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

import static com.bkolomiets.www.core.service.MainService.getLogButtonByRole;
import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;
import static com.bkolomiets.www.core.super_admin.service.SuperAdminService.getAppRoles;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/super_admin")
public class SuperAdminController {
    private final OrganizationService organizationService;
    private final IUserRepository userRepository;

    @GetMapping
    public String superAdmin(final Model model) {
        List<Organization> organizationList = organizationService.getOrganizationList();
        int quantityOrganization = organizationList.size();

        model.addAttribute("quantityOrganization", quantityOrganization);
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());

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
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", MainService.getLogButtonByRole());
        model.addAttribute("rolesList", getAppRoles());

        return "add_organization";
    }

    @PostMapping("/add")
    public String addOrganization(@RequestParam("organizationName") final String organizationName
                                , @RequestParam("productName") final String productName
                                , @RequestParam("username") final String username
                                , @RequestParam("password") final String password
                                , @RequestParam("mail") final String mail
                                , @RequestParam("phone") final Long phone
                                , @RequestParam("description") final String description
                                , @RequestParam("role") final String role
                                ,                       final User user) {
        organizationService.add(organizationName, productName, username, password, mail, phone, description, role);

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);

        return "redirect:/super_admin/all_organization";
    }

    @GetMapping("/all_organization")
    public String all(final Model model) {
        List<Organization> organizationList = organizationService.getOrganizationList();

        model.addAttribute("organizations", organizationList);
        model.addAttribute("quantityOrganization", organizationList.size());
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", MainService.getLogButtonByRole());

        return "/all_organization";
    }
}