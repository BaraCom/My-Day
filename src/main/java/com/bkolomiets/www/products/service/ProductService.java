package com.bkolomiets.www.products.service;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.OrganizationRepository;
import com.bkolomiets.www.products.domain.Product;
import com.bkolomiets.www.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final OrganizationRepository organizationRepository;
    private final IUserRepository userRepository;
    private final ProductRepository productRepository;

    public void add(final String userName
                  , final String productName
                  , final String priceS
                  , final String priceM
                  , final String priceL
                  , final String weightS
                  , final String weightM
                  , final String weightL
                  , final String description) {

        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(user.getUsername(), user.getPassword());
        Set<Product> productList = organization.getProductList();

        if (productList.stream().anyMatch(p -> p.getProductName().equalsIgnoreCase(productName))) {
            // вывести сообщение, что такой продукт уже имеется и, что, если его хотят изменить, нужно зайти в раздел update product
            // и вернуть пользователя на all product
            return;
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
        product.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
        product.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
        product.setWeightS(weightS.equals("") ? null : Integer.valueOf(weightS));
        product.setWeightM(weightM.equals("") ? null : Integer.valueOf(weightM));
        product.setWeightL(weightL.equals("") ? null : Integer.valueOf(weightL));
        product.setDescription(description);

        productList.add(product);

        Product productByName = productRepository.findByProductName(productName);

        if (!productName.equalsIgnoreCase(productByName.getProductName())) {
            productRepository.save(product);
        }
    }

    public Set<Product> getProductList(final String userName) {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(user.getUsername(), user.getPassword());

        return organization.getProductList();
    }
}