package com.tablereservation.customer.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kiosk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kiosk_id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
