package com.pgs.booking.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeOfFlight type;
    @Column(name = "departureDate")
    private LocalDateTime departureDate;
    @Column(name = "arrivalDate")
    private LocalDateTime arrivalDate;

    public enum TypeOfFlight {
        ECONOMY, PREMIUM, BUSINESS
    }
}
