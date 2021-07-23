package com.project.pfe.web;

import com.project.pfe.service.DatabaseFileService;
import com.project.pfe.dao.DiscussionRepository;
import com.project.pfe.dao.UserRepository;
import com.project.pfe.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
public class DiscussionController {
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private DatabaseFileService fileStorageService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/discussion/all")
    public List<Discussion> getAllDiscussions(){
        return discussionRepository.findAll();
    }
    @GetMapping("/discussion/{senderUsername}/{receiverUsername}")
    public List<Discussion> getUserDiscussions(@PathVariable String senderUsername,
                                               @PathVariable String receiverUsername){
        AppUser sender = userRepository.findByUsername(senderUsername);
        AppUser receiver = userRepository.findByUsername(receiverUsername);
        List<Discussion> rs = discussionRepository.findDiscussionBySenderAndReceiver(receiver, sender);
        List<Discussion> sr = discussionRepository.findDiscussionBySenderAndReceiver(sender, receiver);
        return Stream.concat(rs.stream(), sr.stream())
                .collect(Collectors.toList());
    }
    @PostMapping(path = "/discussionWithFile", consumes = {"multipart/form-data"})
    @ResponseBody
    public void addDiscussion(@RequestPart("properties") @Valid DiscussionForm d,
                              @RequestPart("files") @Valid @NotNull @NotBlank MultipartFile[] files){
        Discussion discussion = new Discussion();
        discussion.setDateOfMessage(d.getDateOfMessage());
        discussion.setSender(d.getSender());
        discussion.setReceiver(d.getReceiver());
        discussion.setMessage(d.getMessage());
        List<DatabaseFile> fileNames = Arrays.asList(files)
                .stream()
                .map(file -> fileStorageService.storeFile(file))
                .collect(Collectors.toList());
        discussion.setFiles(fileNames);
        discussionRepository.save(discussion);
    }
    @PostMapping(path = "/discussionWithoutFile")
    public void addDiscussionWithoutFile(@RequestBody DiscussionForm d){
        Discussion discussion = new Discussion();
        discussion.setDateOfMessage(d.getDateOfMessage());
        discussion.setSender(d.getSender());
        discussion.setReceiver(d.getReceiver());
        discussion.setMessage(d.getMessage());
        discussionRepository.save(discussion);
    }
}
