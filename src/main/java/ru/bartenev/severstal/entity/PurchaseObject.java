package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "purchase_object", uniqueConstraints =
@UniqueConstraint(columnNames = {"product_type", "title"}))
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

    @Column(name = "count_of", nullable = false)
    private BigDecimal countOf;

    @ManyToOne
    @JoinColumn(name = "measure_unit", referencedColumnName = "id", nullable = false)
    private MeasureUnit measureUnit;

    @Column(name = "count_of", nullable = false)
    private BigDecimal price_per_unit;

    @ManyToOne
    @JoinColumn(name = "currency_type", referencedColumnName = "id", nullable = false)
    private CurrencyType currencyType;
}
