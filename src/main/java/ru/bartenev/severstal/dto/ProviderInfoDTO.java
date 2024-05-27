package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.Provider;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ProviderInfoDTO {
    private Provider provider;
    private List<InfoDTO> infoDTOList;
    private LocalDate startDay;
    private LocalDate endDay;
}
