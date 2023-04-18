package com.tablereservation.customer.domain.repository;

import com.tablereservation.customer.domain.model.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
    @Query("SELECT k FROM Kiosk k WHERE k.reservation.reservation_id = ?1")
    Kiosk findByReservationId(Long reservationId);
}