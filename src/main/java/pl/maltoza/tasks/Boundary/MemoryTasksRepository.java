package pl.maltoza.tasks.Boundary;

import org.springframework.stereotype.Component;
import pl.maltoza.tasks.Entity.Task;

import java.util.*;

@Component
public class MemoryTasksRepository implements TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task fetchById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found "));

    }

    @Override
    public void deleteById(Long id) {
        findById(id)
                .ifPresent(task -> tasks.remove(task));
    }

    @Override
    public void update(Long id, String title, String description) {
        findById(id)
                .ifPresent(task -> {
                task.setTitle(title);
                task.setDescription(description);
        });
    }

    private Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}
