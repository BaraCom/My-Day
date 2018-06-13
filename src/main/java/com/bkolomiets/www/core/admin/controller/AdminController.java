package com.bkolomiets.www.core.admin.controller;

import com.bkolomiets.www.products.controller.ProductController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.bkolomiets.www.core.service.MainService.getLogButtonByRole;
import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
public class AdminController {
    private final ProductController productController;

    @GetMapping("/all_products")
    public String admin(final Model model, @AuthenticationPrincipal final User user) {
        productController.allProducts(model, user);

        return "all_products";
    }

    @GetMapping("/add_product")
    public String addProduct(final Model model) {
        productController.addProduct(model);

        return "/add_product";
    }

    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam final String productName
                              , @RequestParam final String priceS
                              , @RequestParam final String priceM
                              , @RequestParam final String priceL
                              , @RequestParam final String weightS
                              , @RequestParam final String weightM
                              , @RequestParam final String weightL
                              , @RequestParam final String description
                              , @RequestParam final String category
                              , @AuthenticationPrincipal final User user) {

        productController.addNewProduct(productName, priceS, priceM, priceL, weightS, weightM, weightL, description, category, user);

        return "redirect:/admin/all_products";
    }

    @GetMapping("/update_product")
    public String updateProducts(final Model model, @AuthenticationPrincipal User user) {
        productController.updateProducts(model, user);

        return "update_product";
    }
}
