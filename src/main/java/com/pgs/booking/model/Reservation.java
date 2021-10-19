package com.pgs.booking.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private TypeOfReservation status;

    public enum TypeOfReservation {
        REALIZED, IN_PROGRESS, CANCELED
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Passenger> passengers;
}
