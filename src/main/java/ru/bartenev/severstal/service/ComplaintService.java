package ru.bartenev.severstal.service;

import lombok.NonNull;
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

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Page<Complaint> getComplaintsPageByPurchaseObjectId(Long purchaseObjectId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id"));
        return complaintRepository.findByPurchaseObject_id(purchaseObjectId, pageable);
    }

    public List<Complaint> getComplaintsByPurchaseObjectId(Long purchaseObjectId) {
        return complaintRepository.findByPurchaseObject_id(purchaseObjectId);
    }

    public void checkSumComplaintsCount(@NonNull List<Complaint> complaints, BigDecimal complaintCount, BigDecimal productCount) {
        BigDecimal sumAllComplaintsCount = complaintCount;

        if (!complaints.isEmpty()) {
            sumAllComplaintsCount = complaints
                    .stream()
                    .map(Complaint::getComplaintCount)
                    .reduce(complaintCount, BigDecimal::add);
        }

        if (sumAllComplaintsCount.compareTo(productCount) > 0) {
            throw new InvalidParametersException("Count of all complaints can not be more than count of all products.");
        }
    }

    public Complaint saveComplaint(@NonNull Complaint complaint) {

        checkSumComplaintsCount(
                getComplaintsByPurchaseObjectId(complaint.getPurchaseObject().getId()),
                complaint.getComplaintCount(),
                complaint.getPurchaseObject().getProductCount());

        return complaintRepository.save(complaint);

    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new ComplaintNotFoundException("Complaint with id: " + id + " not found."));
    }

    public void deleteComplaintById(Long id) {
        getComplaintById(id);
        complaintRepository.deleteById(id);
    }

    public Complaint updateComplaint(Complaint newComplaint) {
        List<Complaint> complaints = getComplaintsByPurchaseObjectId(newComplaint.getPurchaseObject().getId());
        complaints.remove(newComplaint);
        checkSumComplaintsCount(
                complaints,
                newComplaint.getComplaintCount(),
                newComplaint.getPurchaseObject().getProductCount());

        return complaintRepository.save(newComplaint);
    }
}
