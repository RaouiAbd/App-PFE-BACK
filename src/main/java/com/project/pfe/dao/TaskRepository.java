package com.project.pfe.dao;

import com.project.pfe.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface TaskRepository extends JpaRepository<Task, Long> {
    public Task findTaskById(Long id);
}
