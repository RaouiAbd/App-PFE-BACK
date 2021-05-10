package com.project.pfe;

import com.project.pfe.Service.AccountService;
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
import java.util.Arrays;

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
            AppUser appUser = accountService.saveUser(new AppUser(null, "admin",
                    "abdelazizraoui3@gmail.com","Administrateur",
                    "0653459000"
                    , "Abuwalid1997",null));
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("admin", "USER");
            AppUser receiver = accountService.saveUser(new AppUser(null, "receiver",
                    "abdelazizraoui3@gmail.com","Administrateur",
                    "0653459000"
                    , "Abuwalid1997",null));
            accountService.addRoleToUser("receiver", "USER");
            Event event = eventRepository.save(new Event(null, "Event",
                    LocalDateTime.of(LocalDate.of(2021,4, 23),
                            LocalTime.of(10, 30))));
            Group groupGeneral = groupRepository.save(new Group(null, "Général",
                    null,Arrays.asList(appUser)));
            Post post=postRepository.save(new Post(null, LocalDateTime.of(
                    LocalDate.of(2021, 3, 10),
                    LocalTime.of(15, 30)), "Test",
                    groupGeneral, null,appUser, null));
            Comment comment = commentRepository.save(new Comment(null, "hhhh", post, appUser));
            discussionRepository.save(new Discussion(null, LocalDateTime.of(
                    LocalDate.of(2021, 3, 10),
                    LocalTime.of(15, 30)), "Test",appUser,receiver,null));
            discussionRepository.save(new Discussion(null, LocalDateTime.of(
                    LocalDate.of(2021, 3, 10),
                    LocalTime.of(15, 30)), "hhhhh",appUser,null,null));


        };
    }

}
