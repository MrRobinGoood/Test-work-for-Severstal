package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DaysReportDTO {
    List<ProviderInfoDTO> providerInfoDays;
    List<AddressInfoDTO> addressInfoDays;
    ProviderInfoDTO providerInfoAllDays;
    AddressInfoDTO addressInfoAllDays;
}
