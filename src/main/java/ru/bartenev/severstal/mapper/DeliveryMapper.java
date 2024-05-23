package ru.bartenev.severstal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.bartenev.severstal.dto.PaginatedDeliveryDTO;
import ru.bartenev.severstal.entity.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(target = "deliveries", source = "deliveryPage.content")
    @Mapping(target = "config.pageNumber", source = "deliveryPage.pageable.pageNumber")
    @Mapping(target = "config.pageSize", source = "deliveryPage.pageable.pageSize")
    @Mapping(target = "config.totalElements", source = "deliveryPage.totalElements")
    @Mapping(target = "config.totalPages", source = "deliveryPage.totalPages")
    PaginatedDeliveryDTO deliveryPageToPaginatedDeliveryDTO (Page<Delivery> deliveryPage);
}
