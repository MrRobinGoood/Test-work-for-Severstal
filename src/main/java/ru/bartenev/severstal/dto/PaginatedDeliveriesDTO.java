package ru.bartenev.severstal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.config.PaginationConfig;
import ru.bartenev.severstal.entity.Delivery;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PaginatedDeliveriesDTO {
    private List<Delivery> deliveries;
    private PaginationConfig config;
}
