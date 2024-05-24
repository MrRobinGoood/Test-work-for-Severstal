package ru.bartenev.severstal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.PurchaseObject;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Page<Complaint> findByPurchaseObject_id(Long purchaseObjectId, Pageable pageable);
}
