package com.project.pfe.dao;

import com.project.pfe.models.AppUser;
import com.project.pfe.models.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findDiscussionBySenderAndReceiver(AppUser sender, AppUser receiver);
}
