package com.bkolomiets.www.products.controller;

import com.bkolomiets.www.core.service.MainService;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all_products")
    public String allProducts(final Model model/*, final User user*/) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", MainService.getLogButtonByRole());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

//        model.addAttribute("productsList", productService.getProductList(user.getPassword(), currentPrincipalName));

        return "all_products";
    }

    @GetMapping("/add_product")
    public String addProduct(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", MainService.getLogButtonByRole());

        return "admin";
    }

    @PostMapping("/add_product")
    public String addNewProduct() {

        return "redirect:/admin/all_products";
    }
}
