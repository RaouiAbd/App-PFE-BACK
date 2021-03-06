package com.project.pfe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data @ToString
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateOfPost;
    private String content;
    @JsonIgnore
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "post")
    private List<Comment> commentaires = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser creator;
    @OneToMany
    @JoinColumn(name = "post_id")
    private List<DatabaseFile> files;

}
