package pl.maltoza.tasks.Boundary;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

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
    void init(){
    tasksService.addTask("Title 1","Description 1");
    tasksService.addTask("Title 2","Description 2");
    tasksService.addTask("Title 3","Description 3");
    }



    @GetMapping
    public List<TaskResponse> getTasks() {
        logger.info("Fetching time...");
        return tasksRepository.fetchAll().stream()
                .map(task -> toTaskResponse(task))
                .collect(toList());
    }

    @GetMapping(path = "/{Id}")
    public TaskResponse getTaskById(@PathVariable Long Id) {
        logger.info("Fetching task no. {} time...", Id);
        return toTaskResponse(tasksRepository.fetchById(Id));
    }

    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        logger.info("Adding time...");
        tasksService.addTask(task.title, task.description);
    }

    @DeleteMapping(path = "/{Id}")
    public void deleteTask(@PathVariable Long Id) {
        logger.info("Deleting task no. {} time...", Id);
        tasksRepository.deleteById(Id);
    }

    @PutMapping
    public void updateTask() {
        logger.info("Updating task...");
    }


    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                LocalDateTime.now());
    }
}
