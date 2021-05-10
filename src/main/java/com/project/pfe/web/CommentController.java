package com.project.pfe.web;

import com.project.pfe.dao.CommentRepository;
import com.project.pfe.dao.PostRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.AppUser;
import com.project.pfe.models.Comment;
import com.project.pfe.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/comment/post/{idPost}")
    private List<Comment> fetchComments(@PathVariable Long idPost){
        Optional<Post> optionalPost = postRepository.findById(idPost);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            return post.getCommentaires();
        }
        return null;
    }
    @PostMapping("comment/{username}/{idPost}")
    private void addComment(@PathVariable String username, @PathVariable  Long idPost,@RequestBody Comment c){
        AppUser  appUser = userRepository.findByUsername(username);
        Optional<Post> optionalPost = postRepository.findById(idPost);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            Comment comment = new Comment();
            comment.setMessage(c.getMessage());
            comment.setPost(post);
            comment.setUser(appUser);
            commentRepository.save(comment);
        }
    }
}
