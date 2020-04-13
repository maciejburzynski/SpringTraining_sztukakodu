package pl.maltoza.tasks.Task;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found "));

    }

    @Override
    public void deleteById(Long id) {
        tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .ifPresent(task -> tasks.remove(task));
    }
}
