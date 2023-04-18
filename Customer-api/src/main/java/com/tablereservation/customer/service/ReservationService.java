package com.tablereservation.customer.service;

import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.repository.ReservationRepository;
import com.tablereservation.customer.excepition.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tablereservation.customer.excepition.ErrorCode.NOT_FOUND_RESERVATION;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(ReservationForm form) {
        Reservation reservation = Reservation.from(form);
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));
    }

    public Reservation updateReservation(Long reservationId, ReservationForm form) {
        Reservation reservation = getReservationById(reservationId);
        reservation.update(form);
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.setReservation_status("CANCELLED");
        reservationRepository.save(reservation);
    }

    public void checkInReservation(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.setReservation_status("체크인 완료");
        reservationRepository.save(reservation);
    }
}