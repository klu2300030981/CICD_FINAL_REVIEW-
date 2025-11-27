package klu.model;

import java.io.Serializable;

// Represents the data structure for a Student, fetched from the student_service.
public class Student implements Serializable {
    
    private Long studentId;
    private String name;
    // Add any other fields your SCFeedback code uses from Student here:

    public Student() {}

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}