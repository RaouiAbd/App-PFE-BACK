package com.project.pfe.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DiscussionForm {
    private LocalDateTime dateOfMessage;
    private String message;
    private AppUser sender;
    private AppUser receiver;
}
