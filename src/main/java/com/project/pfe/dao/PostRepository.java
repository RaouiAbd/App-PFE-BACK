package com.project.pfe.dao;

import com.project.pfe.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@CrossOrigin("*")
public interface PostRepository extends JpaRepository<Post, Long>{
    public List<Post> findAllByOrderByIdDesc();
    public Optional<Post> findById(Long id);
}
