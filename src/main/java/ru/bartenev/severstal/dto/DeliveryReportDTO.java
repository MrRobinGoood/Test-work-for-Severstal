package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryReportDTO {
    private int countPurchaseObjects;
    private int countReceivedPurchaseObjects;
    private int countUnreceivedPurchaseObjects;
    private int countPurchaseObjectsWithComplaints;
    private int countPurchaseObjectsWithoutComplaints;
    private int countFullSuccessfulPurchaseObjects;
    private int numberComplaints;
    private BigDecimal pricePerAllComplaints;
    private BigDecimal totalProductPrice;
    private BigDecimal finalProductPrice;
}
