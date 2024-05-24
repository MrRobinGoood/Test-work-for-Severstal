package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.exception.ComplaintNotFoundException;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.repository.ComplaintRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ComplaintService {
    private ComplaintRepository complaintRepository;
    private PurchaseObjectService purchaseObjectService;
    private ReasonService reasonService;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, PurchaseObjectService purchaseObjectService, ReasonService reasonService) {
        this.complaintRepository = complaintRepository;
        this.purchaseObjectService = purchaseObjectService;
        this.reasonService = reasonService;
    }

    public Page<Complaint> getComplaintsPageByPurchaseObjectId(Long purchaseObjectId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id"));
        return complaintRepository.findByPurchaseObject_id(purchaseObjectId, pageable);
    }

    public List<Complaint> getComplaintsByPurchaseObjectId(Long purchaseObjectId) {
        return complaintRepository.findByPurchaseObject_id(purchaseObjectId);
    }

    public void checkSumComplaintsCount(List<Complaint> complaints, BigDecimal newComplaintCount, BigDecimal productCount) {
        BigDecimal sumAllComplaintsCount = newComplaintCount;

        if (!complaints.isEmpty()) {
            sumAllComplaintsCount = complaints
                    .stream()
                    .map(Complaint::getComplaintCount)
                    .reduce(newComplaintCount, BigDecimal::add);
        }

        if (sumAllComplaintsCount.compareTo(productCount) > 0) {
            throw new InvalidParametersException("Count of all complaints can not be more than count of all products.");
        }
    }

    public Complaint addNewComplaintByPurchaseObjectId(Long purchaseObjectId, Complaint newComplaint) {

        checkSumComplaintsCount(
                getComplaintsByPurchaseObjectId(purchaseObjectId),
                newComplaint.getComplaintCount(),
                newComplaint.getPurchaseObject().getProductCount());

        return complaintRepository.save(newComplaint);

    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new ComplaintNotFoundException("Complaint with id: " + id + " not found."));
    }
}
