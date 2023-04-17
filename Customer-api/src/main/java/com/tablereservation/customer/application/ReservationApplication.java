package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationApplication {
    private final ReservationService reservationService;

    public Long createReservation(ReservationForm form) {
        Reservation reservation = reservationService.createReservation(form);
        return reservation.getReservation_id();
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    public Reservation updateReservation(Long reservationId, ReservationForm form) {
        reservationService.getReservationById(reservationId);

        return reservationService.updateReservation(reservationId, form);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        reservationService.cancelReservation(reservationId);
    }
}