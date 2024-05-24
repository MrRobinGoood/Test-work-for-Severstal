package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import ru.bartenev.severstal.entity.Reason;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class ComplaintDTO {
    private Long id;
    private PurchaseObjectDTO purchaseObject;
    private BigDecimal complaintCount;
    private BigDecimal percentComplaintCount;
    private BigDecimal pricePerAllComplaintCount;
    private Reason reason;
}
