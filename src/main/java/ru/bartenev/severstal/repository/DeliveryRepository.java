package ru.bartenev.severstal.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Delivery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    Page<Delivery> findAll(Pageable pageable);
    Optional<Delivery> findById(Long id);
    List<Delivery> findAllByDeliveryDateTimeBetween(LocalDate startDate, LocalDate endDate);
}
