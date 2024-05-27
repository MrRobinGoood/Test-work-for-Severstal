package ru.bartenev.severstal.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.*;

import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.exception.PurchaseObjectConflictException;
import ru.bartenev.severstal.mapper.PurchaseObjectMapper;
import ru.bartenev.severstal.service.PurchaseObjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/deliveries")
public class PurchaseObjectController {
    private PurchaseObjectService purchaseObjectService;
    private PurchaseObjectMapper purchaseObjectMapper;

    @Autowired
    public PurchaseObjectController(PurchaseObjectService purchaseObjectService, PurchaseObjectMapper purchaseObjectMapper) {
        this.purchaseObjectService = purchaseObjectService;
        this.purchaseObjectMapper = purchaseObjectMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/deliveries/{deliveryId}/purchase-objects")
    public PaginatedPurchaseObjectsDTO getPaginatedPurchaseObjects(
            @PathVariable(value = "deliveryId") Long deliveryId,
            @RequestParam(defaultValue = "1", name = "page") Integer pageNum,
            @RequestParam(defaultValue = "10", name = "size") Integer pageSize) {

        return purchaseObjectMapper.purchaseObjectsPageToPaginatedPurchaseObjectsDTO(
                purchaseObjectService.getPurchaseObjectsPageByDeliveryId(deliveryId, pageNum, pageSize));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/purchase-objects/{purchaseObjectId}")
    public PurchaseObjectDTO getPurchaseObjectById(
            @PathVariable(value = "purchaseObjectId") Long id) {

        return purchaseObjectMapper.purchaseObjectToPurchaseObjectDTO(purchaseObjectService.getPurchaseObjectById(id));
    }


    @PatchMapping(value = "/purchase-objects/{purchaseObjectId}/receiving")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseObjectDTO setPurchaseObjectCheckedById(
            @PathVariable(value = "purchaseObjectId") Long id, @Valid @RequestBody PurchaseObjectUpdateDTO purchaseObjectUpdateDTO) {
        PurchaseObject purchaseObject = purchaseObjectService.getPurchaseObjectById(id);
        if (!purchaseObject.getComplaints().isEmpty()) {
            throw new PurchaseObjectConflictException("Товар с добавленными рекламациями нельзя пометить не полученным.");
        }

        purchaseObjectMapper.updatePurchaseObjectFromPurchaseObjectUpdateDTO(purchaseObjectUpdateDTO, purchaseObject);
        return purchaseObjectMapper.purchaseObjectToPurchaseObjectDTO(purchaseObjectService.savePurchaseObject(purchaseObject));
    }



}
