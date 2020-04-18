package pl.maltoza.tasks.Boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    public List<TaskResponse> getTasks(@RequestParam Optional<String> query) {
        logger.info("Fetching time of {}...", query);
        return query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(task -> toTaskResponse(task))
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskByid(@PathVariable Long id) {
        logger.info("Fetching task no. {} time...", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }


    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename) {
        logger.info("Fetching task no. {} time...", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Handilng file upload: {}", file.getName());
        storageService.saveFile(id,file);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        logger.info("Adding time...");
        tasksService.addTask(task.title, task.description);
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
