package ru.bartenev.severstal.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import ru.bartenev.severstal.dto.*;
import ru.bartenev.severstal.entity.Complaint;
import ru.bartenev.severstal.entity.PurchaseObject;
import ru.bartenev.severstal.entity.Reason;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    @Mapping(target = "percentComplaintCount", expression = "java(complaint.getComplaintCount().divide(complaint.getPurchaseObject().getProductCount(), 4, java.math.RoundingMode.HALF_UP).multiply(new java.math.BigDecimal(\"100\")))")
    @Mapping(target = "pricePerAllComplaintCount", expression = "java(complaint.getPurchaseObject().getPricePerUnit().multiply(complaint.getComplaintCount()))")
    ComplaintDTO complaintToComplaintDTO (Complaint complaint);

    List<ComplaintDTO> complaintsToComplaintDTOList(List<Complaint> complaints);

    @Mapping(target = "config.pageNumber", expression = "java(pageable.getPageNumber() + 1)")
    @Mapping(target = "config.pageSize", source = "complaints.pageable.pageSize")
    @Mapping(target = "config.totalElements", source = "complaints.totalElements")
    @Mapping(target = "config.totalPages", source = "complaints.totalPages")
    @Mapping(target = "complaints", source = "complaints.content")
    PaginatedComplaintsDTO complaintsPageToPaginatedComplaintsDTO(Page<Complaint> complaints);

    Complaint complaintCreationDTOtoComplaint( PurchaseObject purchaseObject, ComplaintCreationDTO complaintCreationDTO, Reason reason);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComplaintFromComplaintCreationDTO(ComplaintCreationDTO complaintCreationDTO, @MappingTarget Complaint complaint);

}
