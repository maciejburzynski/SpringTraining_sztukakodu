package pl.maltoza.tasks.Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maltoza.tasks.Boundary.TasksRepository;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.Entity.Task;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final Clock clock;
    private final AtomicLong nextTaskIdGenerator = new AtomicLong(1);

    @Autowired
    public TasksService(TasksRepository tasksRepository, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
    }

    public void addTask(String title, String description) {
        tasksRepository.add(
                new Task(nextTaskIdGenerator.getAndIncrement(),
                        title,
                        description,
                        clock.time())
        );
    }

    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }
}
