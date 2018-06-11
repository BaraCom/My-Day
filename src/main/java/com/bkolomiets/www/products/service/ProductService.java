package com.bkolomiets.www.products.service;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.category.repository.ICategoryRepository;
import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.data_by_product.DataByProduct;
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
    private final ICategoryRepository categoryRepository;

    public void add(final String userName
                  , final String productName
                  , final String priceS
                  , final String priceM
                  , final String priceL
                  , final String weightS
                  , final String weightM
                  , final String weightL
                  , final String description
                  , final String category) {

        Category byCategory = categoryRepository.findByCategory(category);
        Organization organization = getOrganizationByUserName(userName);
        Set<Product> productList = organization.getProductList();

        Product product;

        if (productList.stream().anyMatch(p -> p.getProductName().equalsIgnoreCase(productName))) {
            // вывести сообщение, что такой продукт уже имеется и, что, если его хотят изменить, нужно зайти в раздел update product
            // и вернуть пользователя на all product
            return;
        }

        if (productRepository.findByProductName(productName) != null) {
            product = productRepository.findByProductName(productName);
        } else {
            product = new Product();
            product.setProductName(productName);
            product.setCategory(byCategory);
        }

        productList.add(product);

        Set<DataByProduct> dataByProductList = organization.getDataByProduct();

        if (dataByProductList.stream().anyMatch(d -> d.getProductName().equalsIgnoreCase(productName))) {
            return;
        } else {
            DataByProduct dataByProduct = new DataByProduct();

            dataByProduct.setProductName(product.getProductName());
            dataByProduct.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
            dataByProduct.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
            dataByProduct.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
            dataByProduct.setWeightS(weightS.equals("") ? null : Integer.valueOf(weightS));
            dataByProduct.setWeightM(weightM.equals("") ? null : Integer.valueOf(weightM));
            dataByProduct.setWeightL(weightL.equals("") ? null : Integer.valueOf(weightL));
            dataByProduct.setDescription(description);
            dataByProduct.setOrganization(organization);

            dataByProductList.add(dataByProduct);
        }

        productRepository.save(product);
    }

    public Set<DataByProduct> getDataByProductList(final String userName) {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return organization.getDataByProduct();
    }

    private Organization getOrganizationByUserName(final String userName) {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return organization;
    }

    private boolean isNullCategory(final Category category) {
        return category == null;

    }
}