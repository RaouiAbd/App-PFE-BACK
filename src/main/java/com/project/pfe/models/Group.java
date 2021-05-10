package com.project.pfe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "group_table")
@NoArgsConstructor @AllArgsConstructor @Data @ToString
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany(mappedBy = "group")
    private List<Post> posts= new ArrayList<>();
    @ManyToMany
    private List<AppUser> users = new ArrayList<>();

}
