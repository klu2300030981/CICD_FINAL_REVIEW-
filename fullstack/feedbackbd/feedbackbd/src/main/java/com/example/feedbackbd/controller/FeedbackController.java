package com.example.feedbackbd.controller;

import com.example.feedbackbd.model.Feedback;
import com.example.feedbackbd.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Feedback submitFeedback(@RequestBody Feedback feedback) {
        feedback.setDate(LocalDateTime.now());
        return feedbackService.saveFeedback(feedback);
    }

    @GetMapping("/student/{studentId}")
    public List<Feedback> getFeedbackByStudent(@PathVariable String studentId) {
        return feedbackService.getFeedbackByStudentId(studentId);
    }

    @GetMapping("/all")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @PutMapping("/{id}/comment")
    public Feedback addAdminComment(@PathVariable String id, @RequestBody String comment) {
        return feedbackService.addAdminComment(id, comment);
    }
}
