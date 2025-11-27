package klu.repository;

import klu.model.SFFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SFFeedbackRepository extends JpaRepository<SFFeedback, Long> {

    /**
     * Finds feedback entries by matching the studentId field within the nested Student object.
     * Assumes SFFeedback entity has a 'Student student' field, and Student DTO has 'Long studentId'.
     */
    List<SFFeedback> findByStudentStudentId(Long studentId);

    /**
     * Finds feedback entries by matching the facultyId field within the nested Faculty object.
     * This method was missing and caused the compilation error.
     * Assumes SFFeedback entity has a 'Faculty faculty' field, and Faculty DTO has 'Long facultyId'.
     */
    List<SFFeedback> findByFacultyFacultyId(Long facultyId);
}