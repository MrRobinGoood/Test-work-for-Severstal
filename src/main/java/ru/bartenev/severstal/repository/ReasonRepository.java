package ru.bartenev.severstal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Reason;

import java.util.Optional;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {
    Optional<Reason> findById(Long id);
}
