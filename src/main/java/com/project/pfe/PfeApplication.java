package com.project.pfe;

import com.project.pfe.Service.AccountService;
import com.project.pfe.dao.PostRepository;
import com.project.pfe.dao.RoleRepository;
import com.project.pfe.models.AppRole;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Post;
import com.project.pfe.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class PfeApplication {

    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;

    @Bean
    public BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(PfeApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PostRepository postRepository){
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(AppUser.class);
            roleRepository.save(new AppRole(null,"USER"));
            roleRepository.save(new AppRole(null,"ADMIN"));
            AppUser appUser = new AppUser(null, "admin",
                    "abdelazizraoui3@gmail.com","Administrateur", "0653459000",
                    Team.IT, "Abuwalid1997",null);
            accountService.saveUser(appUser);
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("admin", "USER");
        };
    }

}
