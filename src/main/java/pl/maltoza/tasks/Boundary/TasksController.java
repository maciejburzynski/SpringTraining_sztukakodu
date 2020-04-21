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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

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
        logger.info("Fetching time of {}...", query);

        List<TaskResponse> collectedTasks = query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(task -> toTaskResponse(task))
                .collect(toList());

        if (collectedTasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok()
                    .body(collectedTasks);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getTaskByid(@PathVariable Long id) {
        try {
            toTaskResponse(tasksRepository.fetchById(id));
        } catch (NotFoundException e) {
            logger.info("Fetching task no {}  no available", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Fetching task no. {} time...", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(toTaskResponse(tasksRepository.fetchById(id)));
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request)
            throws IOException {
        logger.info("Fetching file: {} time...", filename);
        Resource resource = storageService.loadFile(filename);
        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Handilng file upload: {}", file.getName());
        storageService.saveFile(id, file);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity addTask(@RequestBody CreateTaskRequest task) {
        logger.info("Adding time...");
        tasksService.addTask(task.getTitle(), task.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("Deleting task no. {} time...", id);
        tasksRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
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
                task.getCreatedAt());

    }
}
