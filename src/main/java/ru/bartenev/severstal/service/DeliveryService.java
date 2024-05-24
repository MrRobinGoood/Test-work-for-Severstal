package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.Delivery;
import ru.bartenev.severstal.entity.DeliveryStatus;
import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.enums.DeliverySortingFields;
import ru.bartenev.severstal.enums.SortingDirection;
import ru.bartenev.severstal.exception.DeliveryNotFoundException;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.exception.PurchaseObjectNotFoundException;
import ru.bartenev.severstal.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;
    private PurchaseObjectService purchaseObjectService;
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, PurchaseObjectService purchaseObjectService, DeliveryStatusService deliveryStatusService) {
        this.deliveryRepository = deliveryRepository;
        this.purchaseObjectService = purchaseObjectService;
        this.deliveryStatusService = deliveryStatusService;
    }

    public Page<Delivery> getDeliveriesPage(Integer pageNum, Integer pageSize, DeliverySortingFields sortBy, SortingDirection sortDirection) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.fromString(sortDirection.getTitle()),sortBy.getTitle()));
        return deliveryRepository.findAll(pageable);
    }

    public Delivery getDeliveryById(Long id){
        return deliveryRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException("Delivery with id: " + id + " not found."));
    }

    public Delivery completeDeliveryById(Long id){
        Delivery delivery = getDeliveryById(id);
        List<PurchaseObject> purchaseObjects = purchaseObjectService.getPurchaseObjectsByDeliveryId(id);
        delivery.setStatus(deliveryStatusService.getDeliveryStatusById(Long.parseLong("1", 10)));
        int countPurchaseObjectsWithoutComplaints = 0;
        for (PurchaseObject purchaseObject: purchaseObjects){
            if (purchaseObject.getComplaints().isEmpty()) {countPurchaseObjectsWithoutComplaints++;}
            BigDecimal sum = purchaseObject.getComplaints().stream()
                    .map(Complaint::getComplaintCount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (sum.compareTo(purchaseObject.getProductCount()) < 0){
                delivery.setStatus(deliveryStatusService.getDeliveryStatusById(Long.parseLong("3", 10)));
            }
        }
        if (countPurchaseObjectsWithoutComplaints==purchaseObjects.size()){
            delivery.setStatus(deliveryStatusService.getDeliveryStatusById(Long.parseLong("2", 10)));
        }
        return deliveryRepository.save(delivery);
    }
}
