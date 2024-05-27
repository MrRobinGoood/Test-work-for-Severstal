package ru.bartenev.severstal.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Delivery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    Page<Delivery> findAll(Pageable pageable);
    Optional<Delivery> findById(Long id);
    List<Delivery> findAllByDeliveryDateTimeBetweenAndProvider_idInAndAddress_idInAndStatus_titleContains(LocalDateTime startDateTime, LocalDateTime endDateTime, List<Long> providerIdList, List<Long> addressIdList, String status);
    List<Delivery> findAllByDeliveryDateTimeBetweenAndProvider_idInAndStatus_titleContains(LocalDateTime startDateTime, LocalDateTime endDateTime, List<Long> providerIdList, String status);
    List<Delivery> findAllByDeliveryDateTimeBetweenAndAddress_idInAndStatus_titleContains(LocalDateTime startDateTime, LocalDateTime endDateTime, List<Long> addressIdList, String status);
    List<Delivery> findAllByDeliveryDateTimeBetweenAndStatus_titleContains(LocalDateTime startDateTime, LocalDateTime endDateTime, String status);
}
