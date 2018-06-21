package com.bkolomiets.www.products.service;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.category.repository.ICategoryRepository;
import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.data_by_product.domain.DataProduct;
import com.bkolomiets.www.data_by_product.repository.IDataProductRepository;
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
    private final IDataProductRepository dataProductRepository;

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

        if (!isExistProductInOrganization(userName, productName)) {
            Set<Product> productList = organization.getProductList();

            productList.forEach(product -> {
                if (product.getProductName().equalsIgnoreCase(oldProductName)) {
                    Category category = product.getCategory();

//                    product = getProduct(productName, category);
                    product.setProductName(productName);
                    product.setCategory(category);

                    Product byProductName = productRepository.findByProductName(productName);

                    if (byProductName == null) {
                        productRepository.save(new Product(productName, category));
                    }


//                    add(userName, productName, priceS, priceM, priceL, weightS, weightM, weightL, description, category.getCategory());



                    Set<DataProduct> dataProductList = organization.getDataProduct();

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
                            dataProduct.setOrganization(organization);

                            organizationRepository.save(organization);
                            dataProductRepository.save(dataProduct);
                        }
                    });
                }
            });

            /*Set<Product> productList = organization.getProductList();

            Product product = productRepository.findByProductName(oldProductName);
            productList.remove(product);

            Set<DataProduct> dataProductList = organization.getDataProduct();

            DataProduct dataProduct = dataProductRepository.findByProductName(oldProductName);
            dataProductList.remove(dataProduct);

            organizationRepository.save(organization);
            dataProductRepository.delete(dataProduct);

            add(userName, productName, priceS, priceM, priceL, weightS, weightM, weightL, description, "Water (no gaz)");*/
        }
    }

    public Set<DataProduct> getDataProductList(final String userName) throws NullPointerException {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return organization.getDataProduct();
    }

    public Set<DataProduct> getDataProductListByDb(final String userName) {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return dataProductRepository.findAllByOrganization(organization);
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