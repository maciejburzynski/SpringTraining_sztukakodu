package pl.maltoza.tasks.Task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = ("/"))
public class TasksController {
    private final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private final TasksRepository tasksRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks() {
        logger.info("Fetching time...");
        return tasksRepository.fetchAll();
    }
    @PostMapping

    public void addTask(@RequestBody Task task){
        logger.info("Adding time...");
        tasksRepository.add(task);
    }
}
