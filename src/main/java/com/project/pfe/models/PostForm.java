package com.project.pfe.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostForm {
    private LocalDateTime dateOfPost;
    private Group group;
    private String content;
    private AppUser creator;
}
