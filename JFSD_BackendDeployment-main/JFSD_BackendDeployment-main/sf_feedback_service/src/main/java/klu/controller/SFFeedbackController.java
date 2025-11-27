package klu.controller;

import klu.model.SFFeedback;
import klu.repository.SFFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate; // Crucial for microservice communication

import klu.model.Faculty;
import klu.model.Student;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3030/")
@RequestMapping("/feedback")
public class SFFeedbackController {

    // Inject RestTemplate for inter-service communication
    @Autowired
    private RestTemplate restTemplate;

    // Keep this one, as SFFeedback is local to this service's database
    @Autowired
    private SFFeedbackRepository sfFeedbackRepository;

    // Define the base URLs for the external services using Docker hostnames
    private static final String STUDENT_SERVICE_URL = "http://student_service:2001/students";
    // NOTE: Assuming the Faculty service runs on port 2004 as per your dashboard image
    private static final String FACULTY_SERVICE_URL = "http://faculty_service:2004/faculty"; 

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody SFFeedback sfFeedback) {
        if (sfFeedback.getStudent() == null || sfFeedback.getStudent().getStudentId() == null ||
            sfFeedback.getFaculty() == null || sfFeedback.getFaculty().getFacultyId() == null) {
            return ResponseEntity.badRequest().body("Valid Student and Faculty IDs must be provided.");
        }

        try {
            // Fetch the Student entity via HTTP call
            Student student = restTemplate.getForObject(
                STUDENT_SERVICE_URL + "/" + sfFeedback.getStudent().getStudentId(),
                Student.class
            );

            // Fetch the Faculty entity via HTTP call
            Faculty faculty = restTemplate.getForObject(
                FACULTY_SERVICE_URL + "/" + sfFeedback.getFaculty().getFacultyId(),
                Faculty.class
            );
            
            // Check if the services returned a valid object
            if (student == null || faculty == null) {
                return ResponseEntity.badRequest().body("Student or Faculty not found in external services.");
            }

            // Set the fetched DTOs
            sfFeedback.setStudent(student);
            sfFeedback.setFaculty(faculty);

            // Save the feedback
            SFFeedback savedFeedback = sfFeedbackRepository.save(sfFeedback);
            return ResponseEntity.ok(savedFeedback);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error communicating with external services: " + e.getMessage());
        }
    }
    
    // Get all feedback entries
    @GetMapping
    public List<SFFeedback> getAllFeedback() {
        return sfFeedbackRepository.findAll();
    }

    // Get feedback by student ID
    @GetMapping("/student/{studentId}")
    public List<SFFeedback> getFeedbackByStudent(@PathVariable Long studentId) {
        return sfFeedbackRepository.findByStudentStudentId(studentId);
    }

    // Get feedback by faculty ID
    @GetMapping("/faculty/{facultyId}")
    public List<SFFeedback> getFeedbackByFaculty(@PathVariable Long facultyId) {
        return sfFeedbackRepository.findByFacultyFacultyId(facultyId);
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody SFFeedback updatedFeedback) {
        return sfFeedbackRepository.findById(id).map(feedback -> {
            if (updatedFeedback.getFeedback() != null) {
                feedback.setFeedback(updatedFeedback.getFeedback());
            }
            SFFeedback savedFeedback = sfFeedbackRepository.save(feedback);
            return ResponseEntity.ok(savedFeedback);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        return sfFeedbackRepository.findById(id).map(feedback -> {
            sfFeedbackRepository.delete(feedback);
            return ResponseEntity.ok("Feedback deleted successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }
}