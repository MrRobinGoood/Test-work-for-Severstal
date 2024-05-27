package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.Address;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AddressInfoDTO {
    private Address address;
    private List<InfoDTO> infoDTOList;
    private LocalDate startDay;
    private LocalDate endDay;
}
