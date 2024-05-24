package ru.bartenev.severstal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.ComplaintCreationDTO;
import ru.bartenev.severstal.dto.ComplaintDTO;
import ru.bartenev.severstal.dto.PaginatedComplaintsDTO;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.entity.Reason;
import ru.bartenev.severstal.mapper.ComplaintMapper;
import ru.bartenev.severstal.service.ComplaintService;
import ru.bartenev.severstal.service.PurchaseObjectService;
import ru.bartenev.severstal.service.ReasonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/purchase-objects")
public class ComplaintController {

    private ComplaintService complaintService;
    private ComplaintMapper complaintMapper;
    private PurchaseObjectService purchaseObjectService;
    private ReasonService reasonService;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ComplaintMapper complaintMapper, PurchaseObjectService purchaseObjectService, ReasonService reasonService) {
        this.complaintService = complaintService;
        this.complaintMapper = complaintMapper;
        this.purchaseObjectService = purchaseObjectService;
        this.reasonService = reasonService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{purchaseObjectId}/complaints")
    public PaginatedComplaintsDTO getPaginatedComplaintsDTO(@PathVariable(value = "purchaseObjectId") Long purchaseObjectId,
                                                            @RequestParam(defaultValue = "1", name = "page") Integer pageNum,
                                                            @RequestParam(defaultValue = "10", name = "size") Integer pageSize) {
        purchaseObjectService.getPurchaseObjectById(purchaseObjectId);
        return complaintMapper.complaintsPageToPaginatedComplaintsDTO(complaintService.getComplaintsPageByPurchaseObjectId(purchaseObjectId, pageNum, pageSize));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{purchaseObjectId}/complaints")
    public ComplaintDTO addNewComplaintByPurchaseObjectId(@PathVariable(value = "purchaseObjectId") Long purchaseObjectId,
                                                          @Valid @RequestBody ComplaintCreationDTO complaintCreationDTO) {
        PurchaseObject purchaseObject = purchaseObjectService.getPurchaseObjectById(purchaseObjectId);
        Reason reason = reasonService.getReasonById(complaintCreationDTO.getReasonId());
        return complaintMapper.complaintToComplaintDTO(
                complaintService.saveComplaint(complaintMapper.complaintCreationDTOtoComplaint(purchaseObject, complaintCreationDTO, reason)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/complaints/{complaintId}")
    public ComplaintDTO getComplaintDTOById(@PathVariable(value = "complaintId") Long id) {
        return complaintMapper.complaintToComplaintDTO(complaintService.getComplaintById(id));
    }

    @DeleteMapping(value = "/complaints/{complaintId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComplaintById(@PathVariable(value = "complaintId") Long id) {
        complaintService.deleteComplaintById(id);
    }

    @PutMapping(value = "/complaints/{complaintId}")
    @ResponseStatus(HttpStatus.OK)
    public ComplaintDTO updateComplaintById(@PathVariable(value = "complaintId") Long id, @Valid @RequestBody ComplaintCreationDTO complaintCreationDTO) {
        Complaint complaint = complaintService.getComplaintById(id);
        complaintMapper.updateComplaintFromComplaintCreationDTO(complaintCreationDTO, complaint);
        complaint.setReason(reasonService.getReasonById(complaintCreationDTO.getReasonId()));
        return complaintMapper.complaintToComplaintDTO(complaintService.updateComplaint(complaint));
    }

}
