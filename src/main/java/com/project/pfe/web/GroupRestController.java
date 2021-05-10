package com.project.pfe.web;

import com.project.pfe.dao.GroupRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class GroupRestController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/AllGroups")
    public List<Group> fetchGroups(){
         return groupRepository.findAll();
    }

    @PostMapping("/group/user/{idGroup}/{username}")
    public void addUserToGroup(@PathVariable Long idGroup, @PathVariable String username){
        Optional<Group> groupOptional = groupRepository.findById(idGroup);
        AppUser user = userRepository.findByUsername(username);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            for(AppUser forUser: group.getUsers()){
                if(forUser.getUsername() == user.getUsername())
                    throw new RuntimeException("Ce membre existe déjà");

            }
                group.getUsers().add(user);
                groupRepository.save(group);

        }
    }
}
