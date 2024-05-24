package ru.bartenev.severstal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Complaint;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Page<Complaint> findByPurchaseObject_id(Long purchaseObjectId, Pageable pageable);
    List<Complaint> findByPurchaseObject_id(Long purchaseObjectId);
    Optional<Complaint> findById(Long id);
}
