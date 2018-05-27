package com.bkolomiets.www.products.domain;

import com.bkolomiets.www.organization.domain.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    public Product(String productName, Double priceS, Double priceM, Double priceL) {
        this.productName = productName;
        this.priceS = priceS;
        this.priceM = priceM;
        this.priceL = priceL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY/*, generator="product"*/)
//    @SequenceGenerator(name="product", sequenceName="product_id_seq")
    private Long id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "price_s", length = 10, nullable = false)
    private Double priceS;

    @Column(name = "price_m", length = 10, nullable = false)
    private Double priceM;

    @Column(name = "price_l", length = 10, nullable = false)
    private Double priceL;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productList")
    private Set<Organization> organizationList = new HashSet<>();
}
