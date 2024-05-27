package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Delivery;
import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.exception.PurchaseObjectNotFoundException;
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

    public List<PurchaseObject> getPurchaseObjectsByDeliveryId(Long deliveryId) {
        return purchaseObjectRepository.findByDelivery_id(deliveryId);
    }

    public PurchaseObject getPurchaseObjectById(Long id) {
        return purchaseObjectRepository.findById(id).orElseThrow(() -> new PurchaseObjectNotFoundException("Purchase object with id: " + id + " not found."));
    }

    public PurchaseObject savePurchaseObject(PurchaseObject purchaseObject){
        return purchaseObjectRepository.save(purchaseObject);
    }

    public List<PurchaseObject> setPurchaseObjectsIsReceived(List<PurchaseObject> purchaseObjects, Boolean isReceived){
        for (PurchaseObject purchaseObject : purchaseObjects){
            purchaseObject.setIsReceived(isReceived);
        }
        return purchaseObjectRepository.saveAll(purchaseObjects);
    }

    public  List<PurchaseObject> getPurchaseObjectsByDeliveryList(List<Delivery> deliveries){
        return purchaseObjectRepository.findByDeliveryIn(deliveries);
    }

    public Boolean hasPurchaseObjectsComplaints(List<PurchaseObject> purchaseObjects) {
        for (PurchaseObject purchaseObject : purchaseObjects) {
            if (!purchaseObject.getComplaints().isEmpty()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean getInfoPurchaseObjectsDTO(List<PurchaseObject> purchaseObjects) {
        for (PurchaseObject purchaseObject : purchaseObjects) {
            if (!purchaseObject.getComplaints().isEmpty()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
