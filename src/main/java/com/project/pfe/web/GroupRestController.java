package com.project.pfe.web;

import com.project.pfe.dao.GroupRepository;
import com.project.pfe.dao.PostRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Group;
import com.project.pfe.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class GroupRestController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/AllGroups")
    public List<Group> fetchGroups(){
         return groupRepository.findAll();
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroup(@PathVariable(name="id") Long id){
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            for(Post post : group.getPosts()){
                postRepository.deleteById(post.getId());
            }
        }
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/group/user/{idGroup}/{username}")
    public void addUserToGroup(@PathVariable Long idGroup, @PathVariable String username){
        Optional<Group> groupOptional = groupRepository.findById(idGroup);
        AppUser user = userRepository.findByUsername(username);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            for(AppUser forUser: group.getUsers()){
                if(forUser.getUsername().equals(user.getUsername()))
                    throw new RuntimeException("Ce membre existe déjà");

            }
                group.getUsers().add(user);
                groupRepository.save(group);

        }
    }
    @DeleteMapping("/group/user/{idGroup}/{username}")
    public void deleteUserFromGroup(@PathVariable Long idGroup, @PathVariable String username){
        Optional<Group> groupOptional = groupRepository.findById(idGroup);
        AppUser user = userRepository.findByUsername(username);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            group.getUsers().remove(user);
            groupRepository.save(group);

        }
    }
}
