package pl.maltoza.tasks.Boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

@RestController
@Slf4j
@RequestMapping(path = ("/tasks"))
@RequiredArgsConstructor

public class TasksController {
    private final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;
    private final StorageService storageService;


    @PostConstruct
    void init() {
        tasksService.addTask("Title 1", "Description 1");
        tasksService.addTask("Title 2", "Description 2");
        tasksService.addTask("Title 3", "Description 3");
    }


    @GetMapping
    public ResponseEntity getTasks(@RequestParam Optional<String> query) {
        logger.info("Fetching all tasks time ...");
        List<TaskResponse> collectedTasks = query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(task -> toTaskResponse(task))
                .collect(toList());
        return ResponseEntity
                .ok()
                .body(collectedTasks);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getTaskByid(@PathVariable Long id) {
        try {
            toTaskResponse(tasksRepository.fetchById(id));
        } catch (NotFoundException e) {
            logger.info("Fetching task no {}  no available", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Task not found  : " + id);
        }
        logger.info("Fetching task no. {} time...", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toTaskResponse(tasksRepository.fetchById(id)));
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request)
            throws IOException {

        String mimeType;
        Resource resource;

        try {
            logger.info("Fetching file: {} time...", filename);
            resource = storageService.loadFile(filename);
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws
            IOException {
        String filePath = storageService.loadFile(file.getName()).getFile().getPath().toString();
        try {
            tasksRepository.addFilePath(id, file, filePath);
            logger.info("Handling file upload: {}", file.getName());
            storageService.saveFile(id, file);
        } catch (IOException e) {
            logger.info("Unable to upload: {}", file.getName());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        } catch (NotFoundException ne){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ne.getMessage());
        }
        return ResponseEntity
                .noContent()
                .build();
    }


    @PostMapping
    public ResponseEntity addTask(@RequestBody CreateTaskRequest task) {
        logger.info("Adding time...");
        tasksService.addTask(task.getTitle(), task.getDescription());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        logger.info("Deleting task no. {} time...", id);
        try {
            tasksRepository.deleteById(id);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(HttpServletResponse response, @PathVariable Long
            id, @RequestBody UpdateTaskRequest request) {
        try {
            tasksService.updateTask(id, request.title, request.description);
            logger.info("Updating task...");
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            logger.info("unable task updating...");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getFiles());
    }

}
