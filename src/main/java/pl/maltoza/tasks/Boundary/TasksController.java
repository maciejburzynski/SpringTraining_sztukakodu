package pl.maltoza.tasks.Boundary;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@Slf4j
@RequestMapping(path = ("/tasks"))
public class TasksController {
    private final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;


    @Autowired
    public TasksController(TasksRepository tasksRepository, TasksService tasksService) {
        this.tasksRepository = tasksRepository;
        this.tasksService = tasksService;
    }

    @PostConstruct
    void init() {
        tasksService.addTask("Title 1", "Description 1");
        tasksService.addTask("Title 2", "Description 2");
        tasksService.addTask("Title 3", "Description 3");
    }


    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional <String> query) {
        logger.info("Fetching time of {}...",query);
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
    public void updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        logger.info("Updating task...");
        tasksService.updateTask(id, request.title, request.description);
    }


    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt());

    }
}
