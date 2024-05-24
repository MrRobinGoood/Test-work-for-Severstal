package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.PaginatedPurchaseObjectsDTO;
import ru.bartenev.severstal.dto.PurchaseObjectDTO;
import ru.bartenev.severstal.enums.DeliverySortingFields;
import ru.bartenev.severstal.enums.SortingDirection;
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
}
