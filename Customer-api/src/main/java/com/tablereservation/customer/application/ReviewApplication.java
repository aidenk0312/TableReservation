package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.model.Review;
import com.tablereservation.customer.service.ReservationService;
import com.tablereservation.customer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewApplication {
    private final ReviewService reviewService;
    private final ReservationService reservationService;

    public Long createReview(Long reservationId, String comment, Integer rating) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (!"체크인 완료".equals(reservation.getReservation_status())) {
            throw new IllegalStateException("체크인 완료된 예약에 대해서만 리뷰를 작성할 수 있습니다.");
        }

        Review review = Review.builder()
                .reservation(reservation)
                .comment(comment)
                .rating(rating)
                .build();

        Review savedReview = reviewService.createReview(review);
        return savedReview.getReview_id();
    }
}