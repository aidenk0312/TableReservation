package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Kiosk;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.repository.KioskRepository;
import com.tablereservation.customer.domain.repository.ReservationRepository;
import com.tablereservation.customer.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationApplication {
    private final ReservationService reservationService;
    private final KioskRepository kioskRepository;
    private final ReservationRepository reservationRepository;

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

    public void checkIn(Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        reservation.setReservation_status("체크인 완료");
        reservationRepository.save(reservation);

        Kiosk kiosk = Kiosk.builder().kiosk_id(1L).reservation(reservation).build();
        kioskRepository.save(kiosk);
    }

    public void cancelReservationWhenNoCheckInFor10Minutes(Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        Kiosk kiosk = kioskRepository.findByReservationId(reservationId);

        if (kiosk == null && ChronoUnit.MINUTES.between(reservation.getReservation_time(), LocalDateTime.now()) >= 10) {
            reservation.setReservation_status("CANCELLED");
            reservation.setReservation_time(LocalDateTime.now().plusDays(1));
            reservationService.cancelReservation(reservationId);
        }
    }
}