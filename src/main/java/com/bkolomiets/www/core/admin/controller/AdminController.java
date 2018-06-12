package com.bkolomiets.www.core.admin.controller;

import com.bkolomiets.www.core.service.MainService;
import com.bkolomiets.www.core.user_role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.bkolomiets.www.core.service.MainService.getLogButtonByRole;
import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
public class AdminController {

    /*@GetMapping
    public String admin(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());

        if (!MainService.isSameRoleName(Role.ADMIN) || !MainService.isSameRoleName(Role.SUPER_ADMIN)) {
            return "redirect:/";
        }

        return "admin";
    }*/
}
