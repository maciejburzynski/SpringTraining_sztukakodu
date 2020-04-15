package pl.maltoza.tasks.Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maltoza.tasks.Boundary.TasksRepository;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.Entity.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    public List<Task> fetchAll(){
        return tasksRepository.fetchAll();
    }
    public List<Task> filterAllByQuery(String query){
        return tasksRepository.fetchAll()
                .stream()
                .filter(task -> {
                    return task.getTitle().toUpperCase().contains(query.toUpperCase()) ||
                task.getDescription().toUpperCase().contains(query.toUpperCase());
                })
                .collect(toList());
    }
}
