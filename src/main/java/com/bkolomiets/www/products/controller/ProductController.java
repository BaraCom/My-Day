package com.bkolomiets.www.products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/all_products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
}
