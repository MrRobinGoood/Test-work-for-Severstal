package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "complaint")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_object", referencedColumnName = "id", nullable = false)
    private PurchaseObject purchaseObject;

    @Column(name = "count_of", nullable = false)
    private BigDecimal countOf;

    @ManyToOne
    @JoinColumn(name = "reason", referencedColumnName = "id", nullable = false)
    private Reason reason;
}