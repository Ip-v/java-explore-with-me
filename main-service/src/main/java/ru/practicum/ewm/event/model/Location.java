package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Широта и долгота места проведения события
 */
@Getter
@Setter
@ToString
public class Location {
    private double lat;
    private double lon;
}
