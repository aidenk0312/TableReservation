package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.model.Review;
import com.tablereservation.customer.service.ReservationService;
import com.tablereservation.customer.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewApplicationTest {
    @Mock
    private ReviewService reviewService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReviewApplication reviewApplication;

    private Reservation reservation;
    private Review review;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setReservation_id(1L);
        reservation.setReservation_status("체크인 완료");

        review = new Review();
        review.setReview_id(1L);
        review.setReservation(reservation);
        review.setComment("맛집!");
        review.setRating(5);
    }

    @Test
    void createReviewSuccess() {
        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        Long reviewId = reviewApplication.createReview(1L, "맛집!", 5);

        assertEquals(1L, reviewId);
    }
}
