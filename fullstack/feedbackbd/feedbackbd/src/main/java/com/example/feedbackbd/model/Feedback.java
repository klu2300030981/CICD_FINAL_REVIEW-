package com.example.feedbackbd.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedbacks")
public class Feedback {
    @Id
    private String id;
    private String studentId;
    private String studentName;
    private String category; // e.g., Faculty, Labs, Infrastructure, Sports, Clubs, Food, Library
    private String comments; // 1-500 characters validated on frontend
    private LocalDateTime date;
    private AdminComment adminComment;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public AdminComment getAdminComment() {
		return adminComment;
	}
	public void setAdminComment(AdminComment adminComment) {
		this.adminComment = adminComment;
	}

    // getters and setters
}
