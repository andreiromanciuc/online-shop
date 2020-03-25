package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Review;
import org.fasttrackit.onlineshop.persistance.ReviewRepository;
import org.fasttrackit.onlineshop.transfer.review.ReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Page<ReviewResponse> getReviews(long productId, Pageable pageable) {
        LOGGER.info("Retrieving reviews for product {}", productId);

        Page<Review> reviews = reviewRepository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewDtos = new ArrayList<>();

        for (Review review : reviews.getContent()) {
            ReviewResponse dto = new ReviewResponse();
            dto.setId(review.getId());
            dto.setContent(review.getContent());

            reviewDtos.add(dto);
        }

        return new PageImpl<>(reviewDtos, pageable, reviews.getTotalElements());
    }
}
