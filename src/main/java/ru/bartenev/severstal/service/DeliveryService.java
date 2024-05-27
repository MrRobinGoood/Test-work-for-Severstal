package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.dto.DaysReportDTO;
import ru.bartenev.severstal.dto.DeliveryReportDTO;
import ru.bartenev.severstal.dto.InfoDTO;
import ru.bartenev.severstal.entity.*;
import ru.bartenev.severstal.enums.DeliverySortingField;
import ru.bartenev.severstal.enums.SortingDirection;
import ru.bartenev.severstal.exception.DeliveryConflictException;
import ru.bartenev.severstal.exception.DeliveryNotFoundException;
import ru.bartenev.severstal.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

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

    public Page<Delivery> getDeliveriesPage(Integer pageNum, Integer pageSize, DeliverySortingField sortBy, SortingDirection sortDirection) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.fromString(sortDirection.getTitle()), sortBy.getTitle()));
        return deliveryRepository.findAll(pageable);
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException("Delivery with id: " + id + " not found."));
    }

    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery startDeliveryReceivingById(Long id) {
        Delivery delivery = getDeliveryById(id);
        delivery.setStatus(deliveryStatusService.getDeliveryStatusById(2L));
        return saveDelivery(delivery);
    }


    public Delivery cancelDeliveryReceivingById(Long id) {
        Delivery delivery = getDeliveryById(id);
        List<PurchaseObject> purchaseObjects = purchaseObjectService.getPurchaseObjectsByDeliveryId(id);
        if (purchaseObjectService.hasPurchaseObjectsComplaints(purchaseObjects)){
            throw new DeliveryConflictException("Нельзя отменить приёмку товара, для которого уже созданы рекламации.");
        }
//        purchaseObjectService.setPurchaseObjectsIsReceived(purchaseObjects, Boolean.FALSE);
        delivery.setStatus(deliveryStatusService.getDeliveryStatusById(1L));
        return saveDelivery(delivery);
    }

    public List<Delivery> getAllBySomeFilters(LocalDate dateStart, LocalDate dateEnd, List<Long> providerIdList, List<Long> addressIdList, String statusTitle){
        LocalDateTime dateTimeStart = dateStart.atStartOfDay();
        LocalDateTime dateTimeEnd = dateEnd.plusDays(1).atStartOfDay();
        if (providerIdList.isEmpty()&&addressIdList.isEmpty()){
            return deliveryRepository.findAllByDeliveryDateTimeBetweenAndStatus_titleContains(dateTimeStart,dateTimeEnd,statusTitle);
        } else if (providerIdList.isEmpty()) {
            return deliveryRepository.findAllByDeliveryDateTimeBetweenAndAddress_idInAndStatus_titleContains(dateTimeStart,dateTimeEnd,addressIdList,statusTitle);
        } else if (addressIdList.isEmpty()) {
            return deliveryRepository.findAllByDeliveryDateTimeBetweenAndProvider_idInAndStatus_titleContains(dateTimeStart, dateTimeEnd, providerIdList,statusTitle);
        } else {
            return deliveryRepository.findAllByDeliveryDateTimeBetweenAndProvider_idInAndAddress_idInAndStatus_titleContains(dateTimeStart,dateTimeEnd, providerIdList, addressIdList,statusTitle);
        }
    }

    public InfoDTO getInfoBySomeFilters(LocalDate dateStart, LocalDate dateEnd, List<Long> providerIdList, List<Long> addressIdList, String statusTitle){
        List<Delivery> deliveries = getAllBySomeFilters(dateStart,dateEnd, providerIdList, addressIdList, statusTitle);

        List<PurchaseObject> purchaseObjects = purchaseObjectService.getPurchaseObjectsByDeliveryList(deliveries);

        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setDeliveryReportDTO(getDeliveryReportDTO(purchaseObjects));
        infoDTO.setCountDeliveries(deliveries.size());

        return  infoDTO;
    }

    public Delivery finishDeliveryReceivingById(Long id) {
        Delivery delivery = getDeliveryById(id);


        List<PurchaseObject> purchaseObjects = purchaseObjectService.getPurchaseObjectsByDeliveryId(id);
        delivery.setStatus(deliveryStatusService.getDeliveryStatusById(1L));
        int countPurchaseObjectsWithoutComplaints = 0;
        for (PurchaseObject purchaseObject : purchaseObjects) {
            if (purchaseObject.getComplaints().isEmpty()) {
                countPurchaseObjectsWithoutComplaints++;
            }
            BigDecimal sum = purchaseObject.getComplaints().stream()
                    .map(Complaint::getComplaintCount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (sum.compareTo(purchaseObject.getProductCount()) < 0) {
                delivery.setStatus(deliveryStatusService.getDeliveryStatusById(4L));
            }
        }
        if (countPurchaseObjectsWithoutComplaints == purchaseObjects.size()) {
            delivery.setStatus(deliveryStatusService.getDeliveryStatusById(3L));
        }
        return saveDelivery(delivery);
    }

    public DeliveryReportDTO getDeliveryReportDTO(List<PurchaseObject> purchaseObjects){
        DeliveryReportDTO deliveryReportDTO = new DeliveryReportDTO();
        deliveryReportDTO.setCountPurchaseObjects(purchaseObjects.size());
        int countReceivedPurchaseObject = 0;
        int countPurchaseObjectsWithComplaints = 0;
        int countFullSuccessfulPurchaseObjects = 0;
        int numberComplaints = 0;
        BigDecimal pricePerAllComplaints = BigDecimal.ZERO;
        BigDecimal totalProductPrice = BigDecimal.ZERO;

        for (PurchaseObject purchaseObject : purchaseObjects){
            if (purchaseObject.getIsReceived()){countReceivedPurchaseObject++;}
            List<Complaint> complaints = purchaseObject.getComplaints();
            if (!complaints.isEmpty()){countPurchaseObjectsWithComplaints++;}
            if (purchaseObject.getIsReceived()&&complaints.isEmpty()){countFullSuccessfulPurchaseObjects++;}
            numberComplaints += complaints.size();

            BigDecimal allComplaintCount = BigDecimal.ZERO;
            for (Complaint complaint: complaints){
                allComplaintCount = allComplaintCount.add(complaint.getComplaintCount());
            }
            pricePerAllComplaints = pricePerAllComplaints.add(purchaseObject.getProductCount().multiply(allComplaintCount));

            totalProductPrice = totalProductPrice.add(purchaseObject.getProductCount().multiply(purchaseObject.getPricePerUnit()));
        }

        deliveryReportDTO.setCountReceivedPurchaseObjects(countReceivedPurchaseObject);
        deliveryReportDTO.setCountUnreceivedPurchaseObjects(deliveryReportDTO.getCountPurchaseObjects()-countReceivedPurchaseObject);

        deliveryReportDTO.setCountPurchaseObjectsWithComplaints(countPurchaseObjectsWithComplaints);
        deliveryReportDTO.setCountPurchaseObjectsWithoutComplaints(deliveryReportDTO.getCountPurchaseObjects()-countPurchaseObjectsWithComplaints);

        deliveryReportDTO.setCountFullSuccessfulPurchaseObjects(countFullSuccessfulPurchaseObjects);
        deliveryReportDTO.setNumberComplaints(numberComplaints);
        deliveryReportDTO.setPricePerAllComplaints(pricePerAllComplaints);
        deliveryReportDTO.setTotalProductPrice(totalProductPrice);

        deliveryReportDTO.setFinalProductPrice(totalProductPrice.subtract(pricePerAllComplaints));
        return deliveryReportDTO;
    }
}
