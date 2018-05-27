package com.bkolomiets.www.products.service;

import com.bkolomiets.www.products.domain.Product;
import com.bkolomiets.www.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;

    public void add(final String productName, final Double priceS, final Double priceM, final Double priceL) {
        Product product;

        if (isExistProductName(productName)) {
            product = productRepository.findByOrganizationName(productName);
        } else {
            product = new Product();
        }

        product.setProductName(productName);
        product.setPriceS(priceS);
        product.setPriceM(priceM);
        product.setPriceL(priceL);
    }

    public Product findByOrganizationName(final String name) {
        return productRepository.findByOrganizationName(name);
    }

    private boolean isExistProductName(final String name) {
        return productRepository.findAll()
                                .stream()
                                .anyMatch(x -> x.getProductName().equalsIgnoreCase(name)
        );
    }
}
