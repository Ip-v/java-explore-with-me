package ru.practicum.ewm.event.model;

import lombok.*;

import javax.persistence.*;

/**
 * Широта и долгота места проведения события
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    @Column(name = "latitude")
    private double lat;
    @Column(name = "longitude")
    private double lon;
}