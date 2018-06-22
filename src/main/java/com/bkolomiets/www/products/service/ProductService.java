package com.bkolomiets.www.products.service;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.category.repository.ICategoryRepository;
import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.data_by_product.domain.DataProduct;
import com.bkolomiets.www.data_by_product.repository.IDataProductRepository;
import com.bkolomiets.www.data_by_product.service.DataProductService;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.IOrganizationRepository;
import com.bkolomiets.www.organization.service.OrganizationService;
import com.bkolomiets.www.products.domain.Product;
import com.bkolomiets.www.products.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final IOrganizationRepository organizationRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IDataProductRepository dataProductRepository;
    private final OrganizationService organizationService;
    private final DataProductService dataProductService;

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
        Organization organization = organizationService.getOrganizationByUserName(userName);
        Set<Product> productList = organization.getProductList();
        Set<DataProduct> dataProductList = organization.getDataProduct();
        Product product = getProduct(productName, byCategory);

        if (isExistProductInOrganization(userName, productName)) {
            return;
        } else {
            productList.add(product);
            DataProduct dataProduct = dataProductService
                    .createNewDataProduct(product, priceS, priceM, priceL, weightS, weightM, weightL, description, organization);

            dataProductList.add(dataProduct);
        }
        productRepository.save(product);
    }

    public void updateDataProduct(final String oldProductName
                                , final String userName
                                , final String productName
                                , final String priceS
                                , final String priceM
                                , final String priceL
                                , final String weightS
                                , final String weightM
                                , final String weightL
                                , final String description) {

        Organization organization = organizationService.getOrganizationByUserName(userName);

        if (!isExistProductInOrganization(userName, productName)) {
            Set<Product> productList = organization.getProductList();

            if (isExistProductInRepository(productName)) {
                productList.forEach(productByOrganization -> {
                    if (productByOrganization.getProductName().equalsIgnoreCase(oldProductName)) {
                        productList.remove(productByOrganization);
                        productList.add(productRepository.findByProductName(productName));
                    }
                });
            } else {
                productList.forEach(productByOrganization -> {
                    if (productByOrganization.getProductName().equalsIgnoreCase(oldProductName)) {
                        Product product = getProduct(productName, categoryRepository.getOne(1L));

                        productList.add(product);

                        productRepository.save(product);

                        productList.remove(productByOrganization);
                    }
                });
            }

            DataProduct dataProductByOrganization = dataProductRepository.findDataProductByOrganizationAndProductName(organization, oldProductName);

            Set<DataProduct> dataProductList = organization.getDataProduct();
            dataProductList.forEach(dataProductByList -> {
                if (dataProductByList == dataProductByOrganization) {
                    dataProductByList.setProductName(productName);
                    dataProductByList.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
                    dataProductByList.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
                    dataProductByList.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
                    dataProductByList.setWeightS(weightS.equals("") ? null : Double.valueOf(weightS));
                    dataProductByList.setWeightM(weightM.equals("") ? null : Double.valueOf(weightM));
                    dataProductByList.setWeightL(weightL.equals("") ? null : Double.valueOf(weightL));
                    dataProductByList.setDescription(description);
                    dataProductByList.setOrganization(organization);
                }
            });

            organizationRepository.save(organization);
            dataProductRepository.save(dataProductByOrganization);
        }
    }

    public Long getDataProductId(final String userName, final String product) {
        Organization organization = organizationService.getOrganizationByUserName(userName);

        Set<DataProduct> dataProductList = dataProductRepository.findAllByOrganization(organization);

        for (DataProduct dataProduct : dataProductList) {
            if (dataProduct.getProductName().equalsIgnoreCase(product)) {
                return dataProduct.getId();
            }
        }
        return null;
    }

    private boolean isExistProductInRepository(final String productName) {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream().anyMatch(product -> product.getProductName().equalsIgnoreCase(productName));
    }

    private boolean isExistProductInOrganization(final String userName, final String productName) {
        Organization organization = organizationService.getOrganizationByUserName(userName);
        Set<Product> productList = organization.getProductList();

        return productList.stream().anyMatch(d -> d.getProductName().equalsIgnoreCase(productName));
    }

    private Product getProduct(final String productName, final Category category) {
        Product product;

        if (productRepository.findByProductName(productName) != null) {
            product = productRepository.findByProductName(productName);
        } else {
            product = new Product();
            product.setProductName(productName);
            product.setCategory(category);
        }
        return product;
    }
}