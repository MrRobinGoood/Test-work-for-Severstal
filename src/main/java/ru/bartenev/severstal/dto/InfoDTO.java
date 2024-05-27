package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.DeliveryStatus;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class InfoDTO {
    private Integer countDeliveries;
    private DeliveryReportDTO deliveryReportDTO;
}
