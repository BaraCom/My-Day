package com.bkolomiets.www.category.domain;

import com.bkolomiets.www.products.domain.Product;
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
@Getter
@Setter
@NoArgsConstructor
public class Category {
    public Category(String category) {
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> productByCategory = new HashSet<>();
}
