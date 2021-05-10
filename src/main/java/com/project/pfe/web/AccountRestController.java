package com.project.pfe.web;

import com.project.pfe.Service.AccountService;
import com.project.pfe.dao.GroupRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppRole;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Group;
import com.project.pfe.models.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

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
        accountService.saveUser(appUser);
        accountService.addRoleToUser(userForm.getUsername(),"USER");
        if(userForm.getIsAdmin()){
            accountService.addRoleToUser(userForm.getUsername(), "ADMIN");
        }
        if(userForm.getIsResp()){
            accountService.addRoleToUser(userForm.getUsername(), "RESP");
        }
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
    @GetMapping("/users/resp")
    public List<AppUser> getResp(){
        List<AppUser> list = userRepository.findAll();
        List<AppUser> temp = new ArrayList<>();
        for(AppUser user : list){
            for(AppRole role : user.getRoles()){
                if(role.getRoleName() == "RESP"){
                    temp.add(user);
                }
            }
        }
        return temp;
    }
    @GetMapping("/users/group/{id}")
    public List<AppUser> getUsersByGroup(@PathVariable Long id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            return group.getUsers();
        }else {
            throw new RuntimeException("This group doesn't exist");
        }
    }
}
