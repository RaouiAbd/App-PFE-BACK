package com.project.pfe.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentTaskForm {
    private LocalDateTime dateOfMessage;
    private String message;
    private AppUser user;
}
