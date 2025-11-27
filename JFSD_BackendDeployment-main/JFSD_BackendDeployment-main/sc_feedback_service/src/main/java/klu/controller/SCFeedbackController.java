package klu.controller;

import klu.model.SCFeedback;
import klu.repository.SCFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; // Needed for RestTemplate config
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate; // NEW: Used for HTTP calls

import klu.model.Course;
import klu.model.Student;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3030/")
@RequestMapping("/feedback")
public class SCFeedbackController {

    // Inject RestTemplate for inter-service communication
    @Autowired
    private RestTemplate restTemplate;

    // Keep this one, as SCFeedback is local to this service's database
    @Autowired
    private SCFeedbackRepository scFeedbackRepository;

    // Define the base URLs for the external services (using Docker hostnames/ports)
    private static final String STUDENT_SERVICE_URL = "http://student_service:2001/students";
    private static final String COURSE_SERVICE_URL = "http://course_service:2002/courses";

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody SCFeedback scFeedback) {
        if (scFeedback.getStudent() == null || scFeedback.getStudent().getStudentId() == null ||
            scFeedback.getCourse() == null || scFeedback.getCourse().getCourse_Id() == null) {
            return ResponseEntity.badRequest().body("Valid Student and Course IDs must be provided.");
        }

        try {
            // --- REFACTORED: Fetch the Student and Course entities via HTTP call ---
            
            // 1. Fetch Student DTO
            Student student = restTemplate.getForObject(
                STUDENT_SERVICE_URL + "/" + scFeedback.getStudent().getStudentId(),
                Student.class
            );

            // 2. Fetch Course DTO
            Course course = restTemplate.getForObject(
                COURSE_SERVICE_URL + "/" + scFeedback.getCourse().getCourse_Id(),
                Course.class
            );
            
            // Check if the services returned a valid object (null check implies not found/error)
            if (student == null || course == null) {
                return ResponseEntity.badRequest().body("Student or Course not found in external services.");
            }

            // Set the fetched DTOs
            // NOTE: The IDs are carried within the Student/Course DTOs, which is enough for the foreign key reference
            scFeedback.setStudent(student);
            scFeedback.setCourse(course);

            // Save the feedback
            SCFeedback savedFeedback = scFeedbackRepository.save(scFeedback);
            return ResponseEntity.ok(savedFeedback);
            
        } catch (Exception e) {
            // Handle exceptions like service unreachable
            return ResponseEntity.status(500).body("Error communicating with external services: " + e.getMessage());
        }
    }
    
    // Get all feedback entries
    @GetMapping
    public List<SCFeedback> getAllFeedback() {
        return scFeedbackRepository.findAll();
    }

    // Get feedback by student ID
    @GetMapping("/student/{studentId}")
    public List<SCFeedback> getFeedbackByStudent(@PathVariable Long studentId) {
        return scFeedbackRepository.findByStudentStudentId(studentId);
    }

    // Get feedback by course ID
    @GetMapping("/course/{courseId}")
    public List<SCFeedback> getFeedbackByCourse(@PathVariable Long courseId) {
        return scFeedbackRepository.findByCourseId(courseId);
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody SCFeedback updatedFeedback) {
        return scFeedbackRepository.findById(id).map(feedback -> {
            if (updatedFeedback.getFeedback() != null) {
                feedback.setFeedback(updatedFeedback.getFeedback());
            }
            SCFeedback savedFeedback = scFeedbackRepository.save(feedback);
            return ResponseEntity.ok(savedFeedback);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        return scFeedbackRepository.findById(id).map(feedback -> {
            scFeedbackRepository.delete(feedback);
            return ResponseEntity.ok("Feedback deleted successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // If you are using this class as your main application class, uncomment and use this:
    // @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
}