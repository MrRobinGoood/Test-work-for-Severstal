package ru.bartenev.severstal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bartenev.severstal.service.DeliveryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/report")
public class ReportController {
    private DeliveryService deliveryService;

//    public ReportDTO getReportByDateStartAndDateEnd(LocalDate startDate, LocalDate endDate){
//
//    }
}
