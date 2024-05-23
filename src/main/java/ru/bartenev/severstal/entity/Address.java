package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "address")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Delivery> deliveries;
}
