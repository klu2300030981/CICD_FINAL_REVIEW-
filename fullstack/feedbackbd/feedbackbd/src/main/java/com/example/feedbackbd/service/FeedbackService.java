package com.example.feedbackbd.service;

import com.example.feedbackbd.model.AdminComment;
import com.example.feedbackbd.model.Feedback;
import com.example.feedbackbd.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByStudentId(String studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback addAdminComment(String feedbackId, String comment) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow();
        feedback.setAdminComment(new AdminComment());
        feedback.getAdminComment().setComment(comment);
        feedback.getAdminComment().setCommentDate(java.time.LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }
}
