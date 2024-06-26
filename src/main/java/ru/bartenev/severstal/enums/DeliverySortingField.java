package ru.bartenev.severstal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliverySortingField {
    ID("id"),
    PROVIDER("provider"),
    ADDRESS("address"),
    DELIVERY_DATETIME("deliveryDateTime"),
    STATUS("status");

    private String title;
}
