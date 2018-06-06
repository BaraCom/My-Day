package com.bkolomiets.www.products.controller;

import com.bkolomiets.www.core.service.MainService;
import com.bkolomiets.www.products.domain.Product;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

        model.addAttribute("productsList", productService.getProductList(currentPrincipalName));

        return "all_products";
    }

    @GetMapping("/add_product")
    public String addProduct(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", MainService.getLogButtonByRole());

        return "add_product";
    }

    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam final String productName
                              , @RequestParam final String priceS
                              , @RequestParam final String priceM
                              , @RequestParam final String priceL
                              , @RequestParam final String weightS
                              , @RequestParam final String weightM
                              , @RequestParam final String weightL
                              , @RequestParam final String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        productService.add(currentPrincipalName, productName, priceS, priceM, priceL, weightS, weightM, weightL, description);

        return "redirect:/all_products";
    }
}
