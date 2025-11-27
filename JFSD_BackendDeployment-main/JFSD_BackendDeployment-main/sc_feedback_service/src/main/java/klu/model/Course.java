package klu.model;

import java.io.Serializable;

// Represents the data structure for a Course, fetched from the course_service.
public class Course implements Serializable {
    
    private Long courseId;
    private String name;
    // Add any other fields your SCFeedback code uses from Course here:

    public Course() {}

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    // NOTE: This method is specifically added to match the error "getCourse_Id()"
    public Long getCourse_Id() {
        return this.courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}