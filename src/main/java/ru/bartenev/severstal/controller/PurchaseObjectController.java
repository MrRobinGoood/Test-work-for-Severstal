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
@RequestMapping("/delivery")
public class PurchaseObjectController {
    private PurchaseObjectService purchaseObjectService;
    private PurchaseObjectMapper purchaseObjectMapper;

    @Autowired
    public PurchaseObjectController(PurchaseObjectService purchaseObjectService, PurchaseObjectMapper purchaseObjectMapper) {
        this.purchaseObjectService = purchaseObjectService;
        this.purchaseObjectMapper = purchaseObjectMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public PaginatedPurchaseObjectsDTO getPaginatedPurchaseObjects(
            @PathVariable(value = "id") Long deliveryId,
            @RequestParam(defaultValue = "1", name = "page") Integer pageNum,
            @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
            @RequestParam(defaultValue = "DELIVERY_DATETIME", name = "sortBy") String sortBy,
            @RequestParam(defaultValue = "DESC", name = "sortDirection") String sortDirection) {

        return purchaseObjectMapper.purchaseObjectsPageToPaginatedPurchaseObjectsDTO(
                purchaseObjectService.getPurchaseObjectsPageByDeliveryId(deliveryId, pageNum, pageSize));
    }
}
