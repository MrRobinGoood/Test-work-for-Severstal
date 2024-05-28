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
import ru.bartenev.severstal.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private DeliveryService deliveryService;
    private DeliveryMapper deliveryMapper;
    private PurchaseObjectService purchaseObjectService;
    private ProviderService providerService;
    private AddressService addressService;
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, DeliveryMapper deliveryMapper, PurchaseObjectService purchaseObjectService, ProviderService providerService, AddressService addressService, DeliveryStatusService deliveryStatusService) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
        this.purchaseObjectService = purchaseObjectService;
        this.providerService = providerService;
        this.addressService = addressService;
        this.deliveryStatusService = deliveryStatusService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public PaginatedDeliveriesDTO getPaginatedDeliveries(@RequestParam(defaultValue = "1", name = "page") Integer pageNum,
                                                         @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                                         @RequestParam(defaultValue = "0", name = "providerId") Long providerId,
                                                         @RequestParam(defaultValue = "0", name = "addressId") Long addressId,
                                                         @RequestParam(defaultValue = "0", name = "statusId") Long statusId,
                                                         @RequestParam(defaultValue = "ID", name = "sortBy") String sortBy,
                                                         @RequestParam(defaultValue = "DESC", name = "sortDirection") String sortDirection,
                                                         @RequestParam(defaultValue = "1900-01-01", name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                         @RequestParam(defaultValue = "3000-01-01", name = "endDate")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        String providerTitle = "";
        String addressTitle = "";
        String statusTitle = "";
        if (providerId.compareTo(0L) > 0) {providerTitle = providerService.getProviderById(providerId).getTitle();}
        if (addressId.compareTo(0L) > 0) {addressTitle = addressService.getAddressById(addressId).getTitle();}
        if (statusId.compareTo(0L) > 0) {statusTitle =deliveryStatusService.getDeliveryStatusById(statusId).getTitle();}
        LocalDateTime dateTimeStart = startDate.atStartOfDay();
        LocalDateTime dateTimeEnd = endDate.plusDays(1).atStartOfDay();

        return deliveryMapper.deliveriesPageToPaginatedDeliveryDTO(deliveryService.getDeliveriesPage(pageNum, pageSize, DeliverySortingField.valueOf(sortBy), SortingDirection.valueOf(sortDirection),providerTitle,addressTitle,statusTitle, dateTimeStart, dateTimeEnd));
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
    public DeliveryReportDTO getReportDaysDTO(@RequestParam(defaultValue = "1", name = "page") Integer pageNum,
                                    @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                    @RequestParam(defaultValue = "0", name = "providerId") Long providerId,
                                    @RequestParam(defaultValue = "0", name = "addressId") Long addressId,
                                    @RequestParam(defaultValue = "0", name = "statusId") Long statusId,
                                    @RequestParam(defaultValue = "ID", name = "sortBy") String sortBy,
                                    @RequestParam(defaultValue = "DESC", name = "sortDirection") String sortDirection,
                                    @RequestParam(defaultValue = "1900-01-01", name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam(defaultValue = "3000-01-01", name = "endDate")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        String providerTitle = "";
        String addressTitle = "";
        String statusTitle = "";
        if (providerId.compareTo(0L) > 0) {providerTitle = providerService.getProviderById(providerId).getTitle();}
        if (addressId.compareTo(0L) > 0) {addressTitle = addressService.getAddressById(addressId).getTitle();}
        if (statusId.compareTo(0L) > 0) {statusTitle =deliveryStatusService.getDeliveryStatusById(statusId).getTitle();}
        LocalDateTime dateTimeStart = startDate.atStartOfDay();
        LocalDateTime dateTimeEnd = endDate.plusDays(1).atStartOfDay();

        return deliveryService.getDeliveryReportDTO(purchaseObjectService.getPurchaseObjectsByDeliveryList(deliveryService.getDeliveriesPage(pageNum, pageSize, DeliverySortingField.valueOf(sortBy), SortingDirection.valueOf(sortDirection),providerTitle,addressTitle,statusTitle, dateTimeStart, dateTimeEnd).getContent()));
    }


}
