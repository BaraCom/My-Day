package com.bkolomiets.www.core.controller;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.service.MainService;
import com.bkolomiets.www.core.user_role.Role;
import com.bkolomiets.www.core.user_role.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.util.*;

import static com.bkolomiets.www.core.service.MainService.getLogButtonByRole;
import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {
    private final IUserRepository userRepository;

    @GetMapping
    public String main(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());

        return "/main";
    }

    @GetMapping("/registration")
    public String registration(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("repeatPassword") final String repeatPassword, final User user, final Model model) {
        User userName = userRepository.findByUsername(user.getUsername());

        if (!repeatPassword.equals(user.getPassword())) {
            model.addAttribute("notEqualsPass", "Passwords id not equals!");
            return "/registration";
        }

        if (userName != null) {
            model.addAttribute("userExists", "User is exists!");
            return "/registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);

        return "redirect:/login";
    }
}
