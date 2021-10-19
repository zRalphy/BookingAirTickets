package com.pgs.booking.model;

import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.FlightDto;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightDto flight;

    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<CreateUpdatePassengerDto> passengers;

    public enum ReservationStatus {
        IN_PROGRESS, REALIZED, CANCELED
    }
}
