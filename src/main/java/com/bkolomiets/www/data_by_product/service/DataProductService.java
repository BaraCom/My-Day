package com.bkolomiets.www.data_by_product.service;

import com.bkolomiets.www.core.repository.IUserRepository;
import com.bkolomiets.www.core.user_role.User;
import com.bkolomiets.www.data_by_product.domain.DataProduct;
import com.bkolomiets.www.data_by_product.repository.IDataProductRepository;
import com.bkolomiets.www.organization.domain.Organization;
import com.bkolomiets.www.organization.repository.IOrganizationRepository;
import com.bkolomiets.www.products.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataProductService {
    private final IUserRepository userRepository;
    private final IOrganizationRepository organizationRepository;
    private final IDataProductRepository dataProductRepository;

    public Set<DataProduct> getDataProductListByDb(final String userName) {
        User user = userRepository.findByUsername(userName);
        Organization organization = organizationRepository.findByLoginAndPassword(userName, user.getPassword());

        return dataProductRepository.findAllByOrganization(organization);
    }

    public DataProduct createNewDataProduct(final Product product
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

    public DataProduct updateDataProduct(final Set<DataProduct> dataProductList
                                       , final DataProduct dataProductByOrganization
                                       , final String productName
                                       , final String priceS
                                       , final String priceM
                                       , final String priceL
                                       , final String weightS
                                       , final String weightM
                                       , final String weightL
                                       , final String description
                                       , final Organization organization) {
        DataProduct byDataProductList = dataProductList.stream().filter(dataProduct -> dataProduct == dataProductByOrganization).findFirst().get();
        byDataProductList.setProductName(productName);
        byDataProductList.setPriceS(priceS.equals("") ? null : Double.valueOf(priceS));
        byDataProductList.setPriceM(priceM.equals("") ? null : Double.valueOf(priceM));
        byDataProductList.setPriceL(priceL.equals("") ? null : Double.valueOf(priceL));
        byDataProductList.setWeightS(weightS.equals("") ? null : Double.valueOf(weightS));
        byDataProductList.setWeightM(weightM.equals("") ? null : Double.valueOf(weightM));
        byDataProductList.setWeightL(weightL.equals("") ? null : Double.valueOf(weightL));
        byDataProductList.setDescription(description);
        byDataProductList.setOrganization(organization);

        return byDataProductList;
    }

    public DataProduct getDataProductByOrganization(final Organization organization, final String oldProductName) {
        return dataProductRepository.findDataProductByOrganizationAndProductName(organization, oldProductName);
    }
}
