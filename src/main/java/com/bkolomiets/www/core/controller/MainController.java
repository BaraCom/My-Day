package com.bkolomiets.www.core.controller;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.Role;
import com.bkolomiets.www.core.user_role.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {
    private final IUserRepository userRepository;

    @GetMapping
    public String main() {
        return "/main";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(final User user, final Model model) {
        User userName = userRepository.findByUsername(user.getUsername());

        if (userName != null) {
            model.addAttribute("message", "User is exists!");
            return "/registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);

        return "redirect:/login";
    }
}
