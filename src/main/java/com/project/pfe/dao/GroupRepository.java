package com.project.pfe.dao;

import com.project.pfe.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource
@CrossOrigin("*")
public interface GroupRepository extends JpaRepository<Group, Long> {
    public Optional<Group> findById(Long id);
    public Group findGroupsById(Long id);
}
