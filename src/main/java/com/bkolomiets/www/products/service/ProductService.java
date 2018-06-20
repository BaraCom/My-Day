package com.bkolomiets.www.products.service;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.category.repository.ICategoryRepository;
import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.data_by_product.domain.DataProduct;
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
    private final OrganizationService organizationService;

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
            DataProduct dataProduct = createNewDataProduct(product, priceS, priceM, priceL, weightS, weightM, weightL, description, organization);

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
        Set<DataProduct> dataProductList = organization.getDataProduct();
        Set<Product> productList = organization.getProductList();

        if (!isExistProductInOrganization(userName, productName)) {

            productList.forEach(product -> {
                if (product.getProductName().equalsIgnoreCase(oldProductName)) {
                    Category category = product.getCategory();

                    productList.add(getProduct(productName, category));
//                    productList.remove(product);

                    for (Product product1 : productList) {
                        System.out.println(product1.getProductName() + " <<<- prod name: <<<- ");
                    }
                }
            });

            dataProductList.forEach(dataProduct -> {
                if (dataProduct.getProductName().equalsIgnoreCase(oldProductName)) {
                    dataProduct.setProductName(productName);
                    dataProduct.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
                    dataProduct.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
                    dataProduct.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
                    dataProduct.setWeightS(weightS.equals("") ? null : Double.valueOf(weightS));
                    dataProduct.setWeightM(weightM.equals("") ? null : Double.valueOf(weightM));
                    dataProduct.setWeightL(weightL.equals("") ? null : Double.valueOf(weightL));
                    dataProduct.setDescription(description);

                    dataProductList.add(dataProduct);

                    for (DataProduct product : dataProductList) {
                        System.out.println(product.getProductName() + " ->>>  org name: ->>>" + product.getOrganization().getOrganizationName());
                    }
                }
            });

            organizationRepository.save(organization);
        }

        /*if (isExistProductInOrganization(userName, productName)) {
            return;
        } else {
            for (Product product1 : productList) {
                if (product1.getProductName().equalsIgnoreCase(oldProductName)) {
                    Category category = product1.getCategory();

                    Product product2 = getProduct(productName, category);

                    productList.add(product2);

//                                add(userName, productName, priceS, priceM, priceL, weightS, weightM, weightL, description, category.getCategory());
                }
            }
        }*/

        /*for (DataProduct dataProduct : dataProductList) {
            if (dataProduct.getProductName().equalsIgnoreCase(oldProductName)) {
                dataProduct.setProductName(productName);
                dataProduct.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
                dataProduct.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
                dataProduct.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
                dataProduct.setWeightS(weightS.equals("") ? null : Double.valueOf(weightS));
                dataProduct.setWeightM(weightM.equals("") ? null : Double.valueOf(weightM));
                dataProduct.setWeightL(weightL.equals("") ? null : Double.valueOf(weightL));
                dataProduct.setDescription(description);

                dataProductList.add(dataProduct);



                *//*Product changedProduct = productRepository.findByProductName(oldProductName);

                List<Product> allProducts = productRepository.findAll();

                for (Product product : allProducts) {
                    if (product.getProductName().equalsIgnoreCase(productName)) {

                    } else {

                    }
                }*//*
//                changedProduct.setProductName(productName);

//                productRepository.save(changedProduct);
            }
        }*/
//        organizationRepository.save(organization);
    }

    public Set<DataProduct> getDataProductList(final String userName) throws NullPointerException {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return organization.getDataProduct();
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
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

    private DataProduct createNewDataProduct(final Product product
                                               , final String priceS
                                               , final String priceM
                                               , final String priceL
                                               , final String weightS
                                               , final String weightM
                                               , final String weightL
                                               , final String description
                                               , final Organization organization) {
        DataProduct dataProduct = new DataProduct();

        dataProduct.setProductName(product.getProductName());
        dataProduct.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
        dataProduct.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
        dataProduct.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
        dataProduct.setWeightS(weightS.equals("") ? null : Double.valueOf(weightS));
        dataProduct.setWeightM(weightM.equals("") ? null : Double.valueOf(weightM));
        dataProduct.setWeightL(weightL.equals("") ? null : Double.valueOf(weightL));
        dataProduct.setDescription(description);
        dataProduct.setOrganization(organization);

        return dataProduct;
    }
}