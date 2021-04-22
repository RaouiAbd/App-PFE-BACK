package com.project.pfe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data @ToString
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfPost;
    private String photoName;
    private String content;
    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser creator;
    @OneToMany
    @JoinColumn(name = "post_id")
    private List<DatabaseFile> files;

}
