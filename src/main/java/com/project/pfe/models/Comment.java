package com.project.pfe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment_Table")
@NoArgsConstructor @AllArgsConstructor @Data @ToString
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @JsonIgnore
    @ManyToOne
    private Post post;
    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser user;
}
