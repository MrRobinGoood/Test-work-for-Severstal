package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.*;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class PurchaseObjectDTO {
    private Long id;
    private Product product;
    private BigDecimal productCount;
    private MeasureUnit measureUnit;
    private BigDecimal pricePerUnit;
    private BigDecimal pricePerAllCount;
    private CurrencyType currencyType;
    private Boolean hasComplaints;
}
