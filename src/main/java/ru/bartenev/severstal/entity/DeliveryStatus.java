package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "delivery_status")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DeliveryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "deliveryStatus", fetch = FetchType.LAZY)
    private List<Delivery> deliveries;
}