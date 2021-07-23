package com.project.pfe.web;

import com.project.pfe.dao.CommentTaskRepository;
import com.project.pfe.dao.ProjectRepository;
import com.project.pfe.dao.TaskRepository;
import com.project.pfe.models.*;
import com.project.pfe.service.DatabaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
public class TaskRestController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CommentTaskRepository commentTaskRepository;
    @Autowired
    private DatabaseFileService fileStorageService;

    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }
    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable(name = "id") Long id){
        return taskRepository.findTaskById(id);
    }
    @GetMapping("/tasks/project/{id}")
    public Collection<Task> getTasksByProjectId(@PathVariable(name = "id") Long id){
        Project project = projectRepository.findProjectById(id);
        return project.getTasks();


    }
    @GetMapping("/comments/task/{id}")
    public List<CommentTask> getCommentsByTaskId(@PathVariable(name = "id") Long id){
        Task task = taskRepository.findTaskById(id);
        return task.getComments();


    }
    @PostMapping("/tasks/project/{id}")
    public ResponseEntity addTaskToProject(@PathVariable(name = "id") Long id,@RequestBody Task task)
            throws URISyntaxException {
        Task savedTask = new Task();
        savedTask.setDescription(task.getDescription());
        savedTask.setIsOpen(task.getIsOpen());
        Project project = projectRepository.findProjectById(id);
        savedTask.setProject(project);
        taskRepository.save(savedTask);
        return ResponseEntity.created(new URI("/tasks/" + savedTask.getId())).body(savedTask);
    }

    @PostMapping(path = "/commentWithFile/task/{id}", consumes = {"multipart/form-data"})
    @ResponseBody
    public void addCommentWithFileToTask(@PathVariable(name = "id") Long id,
                                 @RequestPart("properties") @Valid CommentTaskForm c,
                                 @RequestPart("files") @Valid @NotNull @NotBlank MultipartFile[] files){
        CommentTask commentTask = new CommentTask();
        commentTask.setDateOfMessage(c.getDateOfMessage());
        commentTask.setMessage(c.getMessage());
        commentTask.setUser(c.getUser());
        List<DatabaseFile> fileNames = Arrays.asList(files)
                .stream()
                .map(file -> fileStorageService.storeFile(file))
                .collect(Collectors.toList());
        commentTask.setFiles(fileNames);
        commentTaskRepository.save(commentTask);
        Task task = taskRepository.findTaskById(id);
        task.getComments().add(commentTask);
        taskRepository.save(task);

    }
    @PostMapping(path = "/commentWithoutFile/task/{id}")
    public void addCommentWithoutFileToTask(@PathVariable(name = "id") Long id,
                                            @RequestBody CommentTaskForm c){
        CommentTask commentTask = new CommentTask();
        commentTask.setDateOfMessage(c.getDateOfMessage());
        commentTask.setMessage(c.getMessage());
        commentTask.setUser(c.getUser());
        commentTaskRepository.save(commentTask);
        Task task = taskRepository.findTaskById(id);
        task.getComments().add(commentTask);
        taskRepository.save(task);

    }
    @PutMapping("/tasks/project/{idProject}/{idTask}")
    public ResponseEntity putTaskState(@PathVariable Long idProject, @PathVariable Long idTask){

        Task task = taskRepository.findTaskById(idTask);
        task.setIsOpen(!task.getIsOpen());
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);

    }
}
