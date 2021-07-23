package com.project.pfe.web;

import com.project.pfe.dao.ProjectRepository;
import com.project.pfe.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProjectRestController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public List<Project> getProjects(){
        return projectRepository.findAll();
    }
    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable Long id){
        return projectRepository.findProjectById(id);
    }
    @PostMapping("/projects")
    public ResponseEntity addProject(@RequestBody Project project) throws URISyntaxException {
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.created(new URI("/projects/" + savedProject.getId())).body(savedProject);
    }
}
