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
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
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

    @GetMapping("/login")
    public String login(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());
        model.addAttribute("loginPage", "");

        return "login";
    }

    @PostMapping("/login")
    public String loginPost(final User user) {
        User userName = userRepository.findByUsername(user.getUsername());

        if (userName != null) {
            return "redirect:/login";
        }

        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());

        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam("repeatPassword") final String repeatPassword
                           , @RequestParam("password") final String password
                           , final User user) {
        User userName = userRepository.findByUsername(user.getUsername());

        if (!password.equals(repeatPassword)) {
            return "redirect:/registration";
        }

        if (userName != null) {
            return "redirect:/registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);

        return "redirect:/login";
    }
}
