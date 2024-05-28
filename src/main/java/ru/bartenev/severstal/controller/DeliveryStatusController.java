package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.entity.DeliveryStatus;
import ru.bartenev.severstal.service.DeliveryStatusService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/delivery-statuses")
public class DeliveryStatusController {
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    public DeliveryStatusController(DeliveryStatusService deliveryStatusService) {
        this.deliveryStatusService = deliveryStatusService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<DeliveryStatus> getAllDeliveryStatuses(){
        return deliveryStatusService.getAllDeliveryStatuses();
    }

}
