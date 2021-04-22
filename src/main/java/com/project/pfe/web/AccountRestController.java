package com.project.pfe.web;



import com.project.pfe.Service.AccountService;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm userForm){

        AppUser user = accountService.findUserByUsername(userForm.getUsername());
        if(user!=null) throw new RuntimeException("This user already exists");
        AppUser appUser = new AppUser();
        appUser.setUsername(userForm.getUsername());
        appUser.setPassword(userForm.getPassword());
        appUser.setEmail(userForm.getEmail());
        appUser.setFunction(userForm.getFunction());
        appUser.setMobile(userForm.getMobile());
        appUser.setTeam(userForm.getTeam());
        accountService.saveUser(appUser);
        accountService.addRoleToUser(userForm.getUsername(),"USER");
        return appUser;
    }
    @GetMapping("/users/user/{username}")
    public AppUser getUser(@PathVariable String username){
        return this.userRepository.findByUsername(username);
    }
    @GetMapping("/users")
    public List<AppUser> getUsers(){
        return this.userRepository.findAll();
    }
}
