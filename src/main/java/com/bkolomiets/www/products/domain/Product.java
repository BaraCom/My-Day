package com.bkolomiets.www.products.domain;

import com.bkolomiets.www.category.domain.Category;
import com.bkolomiets.www.organization.domain.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Borislav Kolomiets
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    public Product(String productName, Double priceS, Double priceM, Double priceL, Integer weightS, Integer weightM, Integer weightL, String description) {
        this.productName = productName;
        this.priceS = priceS;
        this.priceM = priceM;
        this.priceL = priceL;
        this.weightS = weightS;
        this.weightM = weightM;
        this.weightL = weightL;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "price_s", length = 10)
    private Double priceS;

    @Column(name = "price_m", length = 10)
    private Double priceM;

    @Column(name = "price_l", length = 10)
    private Double priceL;

    @Column(name = "weight_s", length = 10)
    private Integer weightS;

    @Column(name = "weight_m", length = 10)
    private Integer weightM;

    @Column(name = "weight_l", length = 10)
    private Integer weightL;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productList")
    private Set<Organization> organizationList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
