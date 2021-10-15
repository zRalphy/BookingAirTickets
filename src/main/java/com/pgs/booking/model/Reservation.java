package com.pgs.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private String status;
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id")
    public User getUser() {
        return user;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private List<Passenger> passengers;
}
