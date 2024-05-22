package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "product", uniqueConstraints =
@UniqueConstraint(columnNames = {"product_type", "title"}))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_type", referencedColumnName = "id", nullable = false)
    private ProductType productType;

    @Column(name = "title", nullable = false)
    private String title;
}
