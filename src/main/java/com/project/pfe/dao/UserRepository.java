package com.project.pfe.dao;


import com.project.pfe.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
public interface UserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);
}
