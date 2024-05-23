package ru.bartenev.severstal.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    Page<Delivery> findAll(@NonNull Pageable pageable);
}
