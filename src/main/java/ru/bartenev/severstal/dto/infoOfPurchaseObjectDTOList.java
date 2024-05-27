package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.MeasureUnit;
import ru.bartenev.severstal.entity.Product;
import ru.bartenev.severstal.entity.Reason;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class infoOfPurchaseObjectDTOList {

    Boolean hasComplaints;
    Boolean hasAllPurchaseObjectComplaints;
    Long numberPurchaseObjects;
    Long numberComplaints;
    Boolean isAllChecked;

    Boolean hasAnyComplaintReasons;
    Boolean hasAnyCurrencies;
    Boolean hasAnyMeasureUnit;
    Boolean hasAnyProduct;

    List<Reason> allTypeReasons;
    List<Currency> allTypeCurrencies;
    List<MeasureUnit> allTypeMeasureUnit;
    List<Product> allTypeProduct;

    BigDecimal allProductsCount;
    BigDecimal allProductsPrice;
    BigDecimal allComplaintsCount;
    BigDecimal allComplaintsPrice;
}
