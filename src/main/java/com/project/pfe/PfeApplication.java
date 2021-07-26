package com.project.pfe;

import com.project.pfe.service.AccountService;
import com.project.pfe.dao.*;
import com.project.pfe.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class PfeApplication {

    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Bean
    public BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(PfeApplication.class, args);
    }

    @Bean
    CommandLineRunner start(){
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(AppUser.class);
            repositoryRestConfiguration.exposeIdsFor(Group.class);
            roleRepository.save(new AppRole(null,"USER"));
            roleRepository.save(new AppRole(null,"ADMIN"));
            roleRepository.save(new AppRole(null, "RESP"));
            String username = "admin";
            AppUser appUser = accountService.saveUser(new AppUser(null, username,
                    "abdelazizraoui3@gmail.com","Administrateur",
                    "0653459000"
                    , "Abuwalid1997",null));
            accountService.addRoleToUser(username, "ADMIN");
            accountService.addRoleToUser(username, "USER");
            AppUser badi = accountService.saveUser(new AppUser(null, "Badi",
                    "abdelazizraoui3@gmail.com","IT Responsible",
                    "0653459000"
                    , "Abuwalid1997",null));
            accountService.addRoleToUser("Badi", "USER");
            Group groupGeneral = groupRepository.save(new Group(null, "General",
                    null,Arrays.asList(appUser)));
            Group groupIT = groupRepository.save(new Group(null, "IT",
                    null,Arrays.asList(appUser)));
            Project project1 = projectRepository.save(new Project(null, "project general", groupGeneral,null));
            Project project2 = projectRepository.save(new Project(null, "project IT", groupIT,null));
            Task task1 = new Task(null, "task 1 of project 1", true,project1, null);
            taskRepository.save(task1);
            Task task2 = taskRepository.save(new Task(null, "task 2 of project 1", true,project1, null));
            Task task3 = taskRepository.save(new Task(null, "task of project 2", true,project2, null));
        };
    }

}
