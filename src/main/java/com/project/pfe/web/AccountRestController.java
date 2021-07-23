package com.project.pfe.web;

import com.project.pfe.service.AccountService;
import com.project.pfe.dao.GroupRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppRole;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Group;
import com.project.pfe.models.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class AccountRestController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;


    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm userForm) throws MessagingException {

        AppUser user = accountService.findUserByUsername(userForm.getUsername());
        if(user!=null) throw new RuntimeException("This user already exists");
        AppUser appUser = new AppUser();
        appUser.setUsername(userForm.getUsername());
        String generatedPassword = accountService.randomString();
        appUser.setPassword(generatedPassword);
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
        try{
            accountService.sendEmail(userForm.getEmail(), userForm.getUsername(), generatedPassword);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return appUser;
    }
    @PutMapping("/users/user/{username}")
    public ResponseEntity updateUser(@PathVariable String username, @RequestBody RegisterForm userForm){
        AppUser user = userRepository.findByUsername(username);
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setFunction(userForm.getFunction());
        user.setMobile(userForm.getMobile());
        accountService.saveUser(user);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/users/user/{id}")
    public ResponseEntity deleteUser(@PathVariable(name="id") Long id){

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();

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
                if(role.getRoleName().equals("RESP")){
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
