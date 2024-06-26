package ru.bartenev.severstal.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.entity.Reason;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class ComplaintCreationDTO {
    @NotNull
    private BigDecimal complaintCount;
    @NotNull
    private Long reasonId;
    @Size(max = 1000)
    private String commentary;
}