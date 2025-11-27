package klu.model;

import java.io.Serializable;

// Represents the data structure for a Faculty member, fetched from the faculty_service.
public class Faculty implements Serializable {
    
    private Long facultyId;
    private String name;
    private String department;
    // Add any other fields your SFFeedback code uses from Faculty here:

    public Faculty() {}

    // Getters and Setters
    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}