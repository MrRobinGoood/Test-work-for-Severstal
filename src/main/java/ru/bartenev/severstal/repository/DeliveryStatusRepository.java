package ru.bartenev.severstal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.DeliveryStatus;

import java.util.Optional;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
    Optional<DeliveryStatus> findById(Long id);
}
