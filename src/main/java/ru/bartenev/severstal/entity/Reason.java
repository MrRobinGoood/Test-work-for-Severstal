package ru.bartenev.severstal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "reason")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Reason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "reason", fetch = FetchType.LAZY)
    private List<Complaint> complaints;
}