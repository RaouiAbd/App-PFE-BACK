package com.project.pfe.web;

import com.project.pfe.Service.DatabaseFileService;
import com.project.pfe.dao.PostRepository;
import com.project.pfe.models.DatabaseFile;
import com.project.pfe.models.Post;
import com.project.pfe.models.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class PostRestController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private DatabaseFileService fileStorageService;


    @GetMapping(path = "/post")
    public List<Post> fetchPosts(){
        return postRepository.findAllByOrderByIdDesc();
    }
    @PostMapping(path = "/postWithFile", consumes = {"multipart/form-data"})
    @ResponseBody
    public void addPost( @RequestPart("properties") @Valid PostForm p,
                         @RequestPart("files") @Valid @NotNull @NotBlank MultipartFile[] files){
        Post post = new Post();
        post.setDateOfPost(p.getDateOfPost());
        post.setPhotoName(p.getPhotoName());
        post.setContent(p.getContent());
        post.setCreator(p.getCreator());
        List<DatabaseFile> fileNames = Arrays.asList(files)
                .stream()
                .map(file -> fileStorageService.storeFile(file))
                .collect(Collectors.toList());
        post.setFiles(fileNames);
        postRepository.save(post);
    }
    @PostMapping(path = "/postWithoutFile")
    public void addPostWithoutFile(@RequestBody PostForm p){
        Post post = new Post();
        post.setDateOfPost(p.getDateOfPost());
        post.setPhotoName(p.getPhotoName());
        post.setContent(p.getContent());
        post.setCreator(p.getCreator());
        postRepository.save(post);
    }

}
