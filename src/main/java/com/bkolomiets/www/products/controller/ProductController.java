package com.bkolomiets.www.products.controller;

import com.bkolomiets.www.data_by_product.domain.DataProduct;
import com.bkolomiets.www.data_by_product.repository.IDataProductRepository;
import com.bkolomiets.www.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static com.bkolomiets.www.core.service.MainService.getLogButtonByRole;
import static com.bkolomiets.www.core.service.MainService.getNavBarByRole;

/**
 * @author Borislav Kolomiets
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;
    private final IDataProductRepository dataProductRepository;
    private DataProduct dataProduct;

    @GetMapping("/all_products")
    public String allProducts(final Model model, @AuthenticationPrincipal final User user) {
        String userName = user.getUsername();

        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());
        model.addAttribute("dataByProductList", productService.getDataProductList(userName));

        return "all_products";
    }

    @GetMapping("/add_product")
    public String addProduct(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());
        model.addAttribute("categoryList", productService.getCategoryList());

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
                              , @RequestParam final String description
                              , @RequestParam final String category
                              , @AuthenticationPrincipal final User user) {
        productService.add(user.getUsername(), productName, priceS, priceM, priceL, weightS, weightM, weightL, description, category);

        return "redirect:/all_products";
    }

    @GetMapping("/update_product")
    public String updateProducts(final Model model, @AuthenticationPrincipal final User user) {
        String currentPrincipalName = user.getUsername();

        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());
        model.addAttribute("dataByProductList", productService.getDataProductList(currentPrincipalName));

        return "update_product";
    }

    @PostMapping("/update_product")
    public String updateProductPost(@RequestParam final String product) {
        dataProduct = dataProductRepository.findByProductName(product);

        return "redirect:/update_product_change";
    }

    @GetMapping("/update_product_change")
    public String updateProductsChange(final Model model) {
        model.addAttribute("navItems", getNavBarByRole());
        model.addAttribute("isLogged", getLogButtonByRole());
        model.addAttribute("dataProduct", dataProduct);

        return "update_product_change";
    }

    @PostMapping("/update_product_change")
    public String updateProductsChangePost(@RequestParam final String productName
                                         , @RequestParam final String priceS
                                         , @RequestParam final String priceM
                                         , @RequestParam final String priceL
                                         , @RequestParam final String weightS
                                         , @RequestParam final String weightM
                                         , @RequestParam final String weightL
                                         , @RequestParam final String description
                                         , @AuthenticationPrincipal final User user) {
        productService.updateDataProduct(user.getUsername(), productName, priceS, priceM, priceL, weightS, weightM, weightL, description);

        return "redirect:/all_products";
    }
}
