package pl.maltoza.tasks.Task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = ("/tasks"))
public class TasksController {
    private final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private final TasksRepository tasksRepository;
    private final TasksConfig config;


    @Autowired
    public TasksController(TasksRepository tasksRepository, TasksConfig config) {
        this.tasksRepository = tasksRepository;
        this.config = config;
    }

    @GetMapping
    public List<Task> getTasks() {
        logger.info("Fetching time...");
        return tasksRepository.fetchAll();
    }
    @GetMapping(path = "/{Id}")
    public Task getTaskById(@PathVariable Long Id) {
        logger.info("Fetching task no. {} time...", Id);
        return tasksRepository.fetchById(Id);
    }
    @PostMapping
    public void addTask(@RequestBody Task task){
        logger.info("Adding time...");
        tasksRepository.add(task);
    }

    @DeleteMapping(path = "/{Id}")
    public void deleteTask(@PathVariable Long Id){
        logger.info("Deleting task no. {} time...",Id);
        tasksRepository.deleteById(Id);
    }
    @PutMapping
    public void updateTask(){
        logger.info("Updating task...");
    }

}
