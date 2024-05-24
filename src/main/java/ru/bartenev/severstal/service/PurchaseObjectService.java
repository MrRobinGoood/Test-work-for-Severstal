package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.dto.PaginatedPurchaseObjectsDTO;
import ru.bartenev.severstal.dto.PurchaseObjectDTO;
import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.enums.DeliverySortingFields;
import ru.bartenev.severstal.enums.SortingDirection;
import ru.bartenev.severstal.mapper.PurchaseObjectMapper;
import ru.bartenev.severstal.repository.PurchaseObjectRepository;

import java.util.List;

@Service
public class PurchaseObjectService {

    private PurchaseObjectRepository purchaseObjectRepository;

    @Autowired
    public PurchaseObjectService(PurchaseObjectRepository purchaseObjectRepository) {
        this.purchaseObjectRepository = purchaseObjectRepository;
    }

    public Page<PurchaseObject> getPurchaseObjectsPageByDeliveryId(Long deliveryId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id"));
        return purchaseObjectRepository.findByDelivery_id(deliveryId, pageable);
    }

}
