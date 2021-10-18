package com.pgs.booking.model;

import lombok.*;
import javax.persistence.*;

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
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    private Flight flight;
     */

    @OneToMany(fetch = FetchType.LAZY)
    private Passenger passenger;
    
}