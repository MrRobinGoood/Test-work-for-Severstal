package ru.bartenev.severstal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryReceivedCommand {
    START("start"),
    CANCEL("cancel"),
    FINISH("finish");
    private String title;
}
