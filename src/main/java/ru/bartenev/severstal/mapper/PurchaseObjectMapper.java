package ru.bartenev.severstal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.bartenev.severstal.config.PaginationConfig;
import ru.bartenev.severstal.dto.PaginatedDeliveriesDTO;
import ru.bartenev.severstal.dto.PaginatedPurchaseObjectsDTO;
import ru.bartenev.severstal.dto.PurchaseObjectDTO;
import ru.bartenev.severstal.entity.PurchaseObject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseObjectMapper {
    @Mapping(target = "pricePerAllCount", expression = "java(purchaseObject.getPricePerUnit().multiply(purchaseObject.getCountOf()))")
    @Mapping(target = "hasComplaints", expression = "java(!purchaseObject.getComplaints().isEmpty())")
    PurchaseObjectDTO purchaseObjectToPurchaseObjectDTO (PurchaseObject purchaseObject);

    List<PurchaseObjectDTO> purchaseObjectsToPurchaseObjectDTOList(List<PurchaseObject> purchaseObjects);

    @Mapping(target = "config.pageNumber", expression = "java(pageable.getPageNumber() + 1)")
    @Mapping(target = "config.pageSize", source = "purchaseObjects.pageable.pageSize")
    @Mapping(target = "config.totalElements", source = "purchaseObjects.totalElements")
    @Mapping(target = "config.totalPages", source = "purchaseObjects.totalPages")
    @Mapping(target = "purchaseObjects", source = "purchaseObjects.content")
    @Mapping(target = "delivery", expression = "java(purchaseObjects.getContent().get(0).getDelivery())")
    PaginatedPurchaseObjectsDTO purchaseObjectsPageToPaginatedPurchaseObjectsDTO(Page<PurchaseObject> purchaseObjects);
}