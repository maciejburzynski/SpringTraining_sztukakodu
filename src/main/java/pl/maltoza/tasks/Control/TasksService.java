package pl.maltoza.tasks.Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maltoza.tasks.Boundary.TasksRepository;
import pl.maltoza.tasks.Entity.Task;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final AtomicLong nextTaskIdGenerator = new AtomicLong(1);

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void addTask(String title, String description) {
        tasksRepository.add(
                new Task(nextTaskIdGenerator.getAndIncrement(),
                        title,
                        description)
        );
    }
}
