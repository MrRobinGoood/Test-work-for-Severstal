package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.repository.ComplaintRepository;

@Service
public class ComplaintService {
    private ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Page<Complaint> getComplainsPageByPurchaseObjectId(Long purchaseObjectId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id"));
        return complaintRepository.findByPurchaseObject_id(purchaseObjectId, pageable);
    }
}
