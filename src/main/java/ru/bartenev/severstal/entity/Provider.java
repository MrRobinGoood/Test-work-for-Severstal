package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "provider")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<Delivery> deliveries;
}
