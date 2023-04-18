package com.tablereservation.customer.service;

import com.tablereservation.customer.domain.model.Review;
import com.tablereservation.customer.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다. 리뷰 ID: " + reviewId));
    }

    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> getReviewsByStoreId(Long storeId, int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String property = sortParams[0];
        String direction = sortParams[1];
        Sort sorting = Sort.by(Sort.Direction.fromString(direction), property);
        PageRequest pageRequest = PageRequest.of(page, size, sorting);
        Page<Review> reviews = reviewRepository.findByReservation_StoreId(storeId, pageRequest);
        return reviews.getContent();
    }
}