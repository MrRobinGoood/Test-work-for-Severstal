package ru.bartenev.severstal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortingDirection {
    ASC("asc"),
    DESC("desc");
    private String title;
}
