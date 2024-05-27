package ru.bartenev.severstal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bartenev.severstal.enums.DeliveryReceivedCommand;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryReceivedCommandDTO {
    @NotNull
    private DeliveryReceivedCommand command;
}
