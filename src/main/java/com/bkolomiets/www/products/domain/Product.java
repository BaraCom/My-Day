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

    public Product(String productName) {
        this.productName = productName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productList")
    private Set<Organization> organizationList = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
