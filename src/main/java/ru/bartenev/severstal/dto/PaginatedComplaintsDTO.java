package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.config.PaginationConfig;
import ru.bartenev.severstal.entity.PurchaseObject;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PaginatedComplaintsDTO {
    private List<ComplaintDTO> complaints;
    private PaginationConfig config;
}
