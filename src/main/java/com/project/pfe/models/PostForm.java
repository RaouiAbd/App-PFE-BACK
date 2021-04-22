package com.project.pfe.models;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
public class PostForm {
    private LocalDate dateOfPost;
    private String photoName;
    private String content;
    private AppUser creator;
}
