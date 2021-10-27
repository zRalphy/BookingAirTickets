package com.pgs.booking.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeOfFlight type;
    @Column(name = "departureDate")
    private LocalDateTime departureDate;
    @Column(name = "arrivalDate")
    private LocalDateTime arrivalDate;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH)
    private List<Reservation> reservations;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "departureAirportId", nullable = false)
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "arrivalAirportId", nullable = false)
    private Airport arrivalAirport;

    public enum TypeOfFlight {
        ECONOMY, PREMIUM, BUSINESS
    }
}
