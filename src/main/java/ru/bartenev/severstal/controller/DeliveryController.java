package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.ComplaintDTO;
import ru.bartenev.severstal.dto.PaginatedDeliveriesDTO;
import ru.bartenev.severstal.entity.Delivery;
import ru.bartenev.severstal.enums.DeliverySortingFields;
import ru.bartenev.severstal.enums.SortingDirection;
import ru.bartenev.severstal.mapper.DeliveryMapper;
import ru.bartenev.severstal.service.DeliveryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private DeliveryService deliveryService;
    private DeliveryMapper deliveryMapper;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, DeliveryMapper deliveryMapper) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public PaginatedDeliveriesDTO getPaginatedDeliveries(@RequestParam(defaultValue = "1", name = "page") Integer pageNum,
                                                         @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                                         @RequestParam(defaultValue = "ID", name = "sortBy") String sortBy,
                                                         @RequestParam(defaultValue = "DESC", name = "sortDirection") String sortDirection) {
        return deliveryMapper.deliveriesPageToPaginatedDeliveryDTO(deliveryService.getDeliveriesPage(pageNum, pageSize, DeliverySortingFields.valueOf(sortBy), SortingDirection.valueOf(sortDirection)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{deliveryId}")
    public Delivery getDeliveryById(@PathVariable(value = "deliveryId") Long id) {
        return deliveryService.getDeliveryById(id);
    }
}
