package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.PaginatedComplaintsDTO;
import ru.bartenev.severstal.dto.PaginatedPurchaseObjectsDTO;
import ru.bartenev.severstal.mapper.ComplaintMapper;
import ru.bartenev.severstal.service.ComplaintService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/purchase-objects")
public class ComplaintController {

    private ComplaintService complaintService;
    private ComplaintMapper complaintMapper;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ComplaintMapper complaintMapper) {
        this.complaintService = complaintService;
        this.complaintMapper = complaintMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/complaints")
    public PaginatedComplaintsDTO getPaginatedComplaintsDTO(
            @PathVariable(value = "id") Long purchaseObjectId,
            @RequestParam(defaultValue = "1", name = "page") Integer pageNum,
            @RequestParam(defaultValue = "10", name = "size") Integer pageSize) {

        return complaintMapper.complaintsPageToPaginatedComplaintsDTO(
                complaintService.getComplainsPageByPurchaseObjectId(purchaseObjectId, pageNum, pageSize));    }
}
