package ru.bartenev.severstal.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.Reason;
import ru.bartenev.severstal.exception.ComplaintConflictException;
import ru.bartenev.severstal.exception.ComplaintNotFoundException;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.repository.ComplaintRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void checkSumComplaintsCount(@NonNull List<Complaint> complaints, Complaint newComplaint) {
        BigDecimal sumAllComplaintsCount = newComplaint.getComplaintCount();
        BigDecimal sumComplaintsCountWithReasonNotDelivered = new BigDecimal(0);
        if (newComplaint.getReason().getId() == 1) {
            sumComplaintsCountWithReasonNotDelivered = newComplaint.getComplaintCount();
        }



        if (!complaints.isEmpty()) {
            for (Complaint complaint : complaints) {
                if (complaint.getReason().getId() == newComplaint.getReason().getId()){
                    throw new InvalidParametersException("Рекламация с данной причиной уже создана. Пожалуйста измените количество уже существующей рекламации.");
                }
                if (complaint.getReason().getId() == 1L && newComplaint.getReason().getId() == 1) {
                    sumComplaintsCountWithReasonNotDelivered = sumComplaintsCountWithReasonNotDelivered.add(complaint.getComplaintCount());
                }
                sumAllComplaintsCount = sumAllComplaintsCount.add(complaint.getComplaintCount());
            }
        }

        if (sumAllComplaintsCount.compareTo(newComplaint.getPurchaseObject().getProductCount()) > 0) {
            throw new InvalidParametersException("Количество всех рекламаций не может превышать общее количество товаров.");
        }

        if (sumComplaintsCountWithReasonNotDelivered.compareTo(newComplaint.getPurchaseObject().getProductCount()) == 0) {
            throw new InvalidParametersException("Количество рекламации со статусом 'Не поставлено' не может составлять 100% продукта. " +
                    "Если весь товар не поставлен, пожалуйста укажите это в списке объектов закупки.");
        }
    }

    public void checkComplaintDeliveryStatus(Complaint complaint) {
        if (complaint.getPurchaseObject().getDelivery().getStatus().getId() == 1) {
            throw new ComplaintConflictException("Процедура приёмки доставки еще не начата.");
        }
        if (complaint.getPurchaseObject().getDelivery().getStatus().getId() > 2) {
            throw new ComplaintConflictException("Текущая доставка уже завершена.");
        }
    }

    public Complaint saveComplaint(@NonNull Complaint complaint) {
        checkComplaintDeliveryStatus(complaint);
        checkSumComplaintsCount(getComplaintsByPurchaseObjectId(complaint.getPurchaseObject().getId()), complaint);

        return complaintRepository.save(complaint);

    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new ComplaintNotFoundException("Complaint with id: " + id + " not found."));
    }

    public void deleteComplaintById(Long id) {
        checkComplaintDeliveryStatus(getComplaintById(id));
        complaintRepository.deleteById(id);
    }

    public Complaint updateComplaint(Complaint newComplaint) {
        checkComplaintDeliveryStatus(newComplaint);
        List<Complaint> complaints = getComplaintsByPurchaseObjectId(newComplaint.getPurchaseObject().getId());
        complaints.remove(newComplaint);
        checkSumComplaintsCount(complaints, newComplaint);

        return complaintRepository.save(newComplaint);
    }
}
