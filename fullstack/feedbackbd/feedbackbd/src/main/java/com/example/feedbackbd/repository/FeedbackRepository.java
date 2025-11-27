package com.example.feedbackbd.repository;

import com.example.feedbackbd.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByStudentId(String studentId);
}
