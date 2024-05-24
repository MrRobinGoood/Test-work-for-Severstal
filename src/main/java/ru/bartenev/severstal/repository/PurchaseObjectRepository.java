package ru.bartenev.severstal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.PurchaseObject;

import java.util.List;

@Repository
public interface PurchaseObjectRepository extends JpaRepository<PurchaseObject, Long> {
    Page<PurchaseObject> findByDelivery_id(Long deliveryId, Pageable pageable);
}
