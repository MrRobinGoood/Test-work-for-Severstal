package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "purchase_object")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PurchaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery", referencedColumnName = "id", nullable = false)
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "productCount", nullable = false)
    private BigDecimal productCount;

    @ManyToOne
    @JoinColumn(name = "measure_unit", referencedColumnName = "id", nullable = false)
    private MeasureUnit measureUnit;

    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "currency_type", referencedColumnName = "id", nullable = false)
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "purchaseObject", fetch = FetchType.LAZY)
    private List<Complaint> complaints;
}

