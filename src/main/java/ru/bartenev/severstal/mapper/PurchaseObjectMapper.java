package ru.bartenev.severstal.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import ru.bartenev.severstal.config.PaginationConfig;
import ru.bartenev.severstal.dto.*;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.PurchaseObject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseObjectMapper {

    @Mapping(target = "pricePerAllCount", expression = "java(purchaseObject.getPricePerUnit().multiply(purchaseObject.getProductCount()))")
    @Mapping(target = "hasComplaints", expression = "java(!purchaseObject.getComplaints().isEmpty())")
    @Mapping(target = "isReceived", source = "isReceived")
    @Mapping(target = "deliveryId", source = "delivery.id")
    PurchaseObjectDTO purchaseObjectToPurchaseObjectDTO (PurchaseObject purchaseObject);

    List<PurchaseObjectDTO> purchaseObjectsToPurchaseObjectDTOList(List<PurchaseObject> purchaseObjects);

    @Mapping(target = "config.pageNumber", expression = "java(pageable.getPageNumber() + 1)")
    @Mapping(target = "config.pageSize", source = "purchaseObjects.pageable.pageSize")
    @Mapping(target = "config.totalElements", source = "purchaseObjects.totalElements")
    @Mapping(target = "config.totalPages", source = "purchaseObjects.totalPages")
    @Mapping(target = "purchaseObjects", source = "purchaseObjects.content")
    PaginatedPurchaseObjectsDTO purchaseObjectsPageToPaginatedPurchaseObjectsDTO(Page<PurchaseObject> purchaseObjects);

    @Mapping(target = "isReceived", source = "isReceived")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePurchaseObjectFromPurchaseObjectUpdateDTO(PurchaseObjectUpdateDTO purchaseObjectUpdateDTO, @MappingTarget PurchaseObject purchaseObject);
}