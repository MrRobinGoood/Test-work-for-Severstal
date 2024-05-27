package ru.bartenev.severstal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.DeliveryReceivedCommandDTO;
import ru.bartenev.severstal.dto.DeliveryReportDTO;
import ru.bartenev.severstal.dto.InfoDTO;
import ru.bartenev.severstal.dto.PaginatedDeliveriesDTO;
import ru.bartenev.severstal.entity.Delivery;
import ru.bartenev.severstal.enums.DeliveryReceivedCommand;
import ru.bartenev.severstal.enums.DeliverySortingField;
import ru.bartenev.severstal.enums.SortingDirection;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.mapper.DeliveryMapper;
import ru.bartenev.severstal.service.DeliveryService;
import ru.bartenev.severstal.service.PurchaseObjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private DeliveryService deliveryService;
    private DeliveryMapper deliveryMapper;
    private PurchaseObjectService purchaseObjectService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, DeliveryMapper deliveryMapper, PurchaseObjectService purchaseObjectService) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
        this.purchaseObjectService = purchaseObjectService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public PaginatedDeliveriesDTO getPaginatedDeliveries(@RequestParam(defaultValue = "1", name = "page") Integer pageNum,
                                                         @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                                         @RequestParam(defaultValue = "ID", name = "sortBy") String sortBy,
                                                         @RequestParam(defaultValue = "DESC", name = "sortDirection") String sortDirection) {
        return deliveryMapper.deliveriesPageToPaginatedDeliveryDTO(deliveryService.getDeliveriesPage(pageNum, pageSize, DeliverySortingField.valueOf(sortBy), SortingDirection.valueOf(sortDirection)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{deliveryId}")
    public Delivery getDeliveryById(@PathVariable(value = "deliveryId") Long id) {
        return deliveryService.getDeliveryById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{deliveryId}/report")
    public DeliveryReportDTO getDeliveryReportDTOById(@PathVariable(value = "deliveryId") Long id) {
        deliveryService.getDeliveryById(id);
        DeliveryReportDTO deliveryReportDTO = deliveryService.getDeliveryReportDTO(purchaseObjectService.getPurchaseObjectsByDeliveryId(id));

        return deliveryReportDTO;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{deliveryId}/receiving")
    public Delivery finishDeliveryById(@PathVariable(value = "deliveryId") Long id, @Valid @RequestBody DeliveryReceivedCommandDTO command) {
        if (command.getCommand() == DeliveryReceivedCommand.START) {
            return deliveryService.startDeliveryReceivingById(id);
        } else if (command.getCommand() == DeliveryReceivedCommand.CANCEL) {
            return deliveryService.cancelDeliveryReceivingById(id);
        } else if (command.getCommand() == DeliveryReceivedCommand.FINISH) {
            return deliveryService.finishDeliveryReceivingById(id);
        } else {
            throw new InvalidParametersException("Указана неверная команда 'command'.");
        }


    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/report")
    public InfoDTO getReportDaysDTO(@RequestParam(defaultValue = "01.01.1900", name = "dateStart") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate dateStart,
                                    @RequestParam(defaultValue = "01.01.3000", name = "dateEnd")@DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate dateEnd,
                                    @RequestParam(defaultValue = "", name = "providerIdList") Set<Long> providerIdList,
                                    @RequestParam(defaultValue = "", name = "addressIdList") Set<Long> addressIdList,
                                    @RequestParam(defaultValue = "", name = "statusTitle") String statusTitle) {

        return deliveryService.getInfoBySomeFilters(dateStart,dateEnd,providerIdList.stream().toList(),addressIdList.stream().toList(),statusTitle);
    }


}
