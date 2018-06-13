package com.bkolomiets.www.data_by_product.domain;

import com.bkolomiets.www.organization.domain.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

/**
 * @author Borislav Kolomiets
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DataProduct {

    public DataProduct(Double priceS, Double priceM, Double priceL, Double weightS, Double weightM, Double weightL, String description) {
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
    private Double weightS;

    @Column(name = "weight_m", length = 10)
    private Double weightM;

    @Column(name = "weight_l", length = 10)
    private Double weightL;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}