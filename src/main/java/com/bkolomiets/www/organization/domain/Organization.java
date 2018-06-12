package com.bkolomiets.www.organization.domain;

import com.bkolomiets.www.data_by_product.domain.DataProduct;
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
@NoArgsConstructor
@Getter
@Setter
public class Organization {

    public Organization(String organizationName, String login, String password, String mail, Long phone) {
        this.organizationName = organizationName;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_name", length = 50, nullable = false)
    private String organizationName;

    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "mail", length = 50, nullable = false)
    private String mail;

    @Column(name = "phone", length = 12, nullable = false)
    private Long phone;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_organization",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> productList = new HashSet<>();

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DataProduct> dataProduct = new HashSet<>();
}
