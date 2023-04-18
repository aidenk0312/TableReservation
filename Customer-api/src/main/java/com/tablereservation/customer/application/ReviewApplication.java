package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReviewDto;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.model.Review;
import com.tablereservation.customer.excepition.CustomException;
import com.tablereservation.customer.service.ReservationService;
import com.tablereservation.customer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tablereservation.customer.excepition.ErrorCode.NOT_FOUND_CHECKIN;

@Service
@RequiredArgsConstructor
public class ReviewApplication {
    private final ReviewService reviewService;
    private final ReservationService reservationService;

    public Long createReview(Long reservationId, String comment, Integer rating) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (!"체크인 완료".equals(reservation.getReservation_status())) {
            throw new CustomException(NOT_FOUND_CHECKIN);
        }

        Review review = Review.builder()
                .reservation(reservation)
                .comment(comment)
                .rating(rating)
                .build();

        Review savedReview = reviewService.createReview(review);
        return savedReview.getReview_id();
    }

    public ReviewDto getReviewDto(Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ReviewDto.builder()
                .review_id(review.getReview_id())
                .reservation_id(review.getReservation().getReservation_id())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }

    public void updateReview(Long reviewId, String comment, Integer rating) {
        Review review = reviewService.getReviewById(reviewId);

        review.setComment(comment);
        review.setRating(rating);

        reviewService.updateReview(review);
    }

    public void deleteReview(Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    public List<ReviewDto> getReviewsByStoreId(Long storeId, int page, int size, String sort) {
        List<Review> reviews = reviewService.getReviewsByStoreId(storeId, page, size, sort);
        return reviews.stream()
                .map(review -> ReviewDto.builder()
                        .review_id(review.getReview_id())
                        .reservation_id(review.getReservation().getReservation_id())
                        .comment(review.getComment())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());
    }
}