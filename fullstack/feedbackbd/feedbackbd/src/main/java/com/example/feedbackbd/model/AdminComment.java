package com.example.feedbackbd.model;

import java.time.LocalDateTime;

public class AdminComment {
    private String comment;
    private LocalDateTime commentDate;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }
}
