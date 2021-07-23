package com.project.pfe.web;

import com.project.pfe.service.DatabaseFileService;
import com.project.pfe.dao.GroupRepository;
import com.project.pfe.dao.NotificationRepository;
import com.project.pfe.dao.PostRepository;
import com.project.pfe.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class PostRestController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private DatabaseFileService fileStorageService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NotificationRepository notificationRepository;


    @GetMapping(path = "/post")
    public List<Post> fetchPosts() {
        return postRepository.findAll();
    }

    @PostMapping(path = "/postWithFile", consumes = {"multipart/form-data"})
    @ResponseBody
    public void addPost( @RequestPart("properties") @Valid PostForm p,
                         @RequestPart("files") @Valid @NotNull @NotBlank MultipartFile[] files){
        Post post = new Post();
        post.setDateOfPost(p.getDateOfPost());
        post.setContent(p.getContent());
        post.setGroup(p.getGroup());
        post.setCreator(p.getCreator());
        List<DatabaseFile> fileNames = Arrays.asList(files)
                .stream()
                .map(file -> fileStorageService.storeFile(file))
                .collect(Collectors.toList());
        post.setFiles(fileNames);
        postRepository.save(post);
        notificationRepository.save(new Notification(null, "Nouvelle Publication"));
    }
    @PostMapping(path = "/postWithoutFile")
    public void addPostWithoutFile(@RequestBody PostForm p){
        Post post = new Post();
        post.setDateOfPost(p.getDateOfPost());
        post.setContent(p.getContent());
        post.setCreator(p.getCreator());
        post.setGroup(p.getGroup());
        postRepository.save(post);
    }
    @GetMapping("/posts/group/{id}")
    public List<Post> fetchPosts(@PathVariable Long id){
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            return group.getPosts();
        } else{
            return Collections.emptyList();
        }
    }
    @GetMapping("/comments/post/{id}")
    public List<Comment> fetchComments(@PathVariable Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            return post.getCommentaires();
        } else{
            return Collections.emptyList();
        }
    }

}
