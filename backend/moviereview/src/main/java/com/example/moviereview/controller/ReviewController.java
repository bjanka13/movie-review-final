package com.example.moviereview.controller;

import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;
import com.example.moviereview.repository.MovieRepository;
import com.example.moviereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @PostMapping("/movie/{movieId}")
    public ResponseEntity<Review> addReview(@PathVariable Long movieId, @RequestBody Review review) {
        return movieRepository.findById(movieId).map(movie -> {
            review.setMovie(movie);
            return ResponseEntity.ok(reviewRepository.save(review));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }
}
