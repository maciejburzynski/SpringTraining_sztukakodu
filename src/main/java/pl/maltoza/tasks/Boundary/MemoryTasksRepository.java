package pl.maltoza.tasks.Boundary;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tasks.Entity.Task;

import java.util.*;

@Getter
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
                .orElseThrow(() -> new NotFoundException("Task not found : " + id));

    }

    @Override
    public void deleteById(Long id) {
        findById(id)
                .ifPresentOrElse(tasks::remove, () -> {
                    throw new NotFoundException("Task not found  : " + id);
                });
    }

    @Override
    public void update(Long id, String title, String description) {
        Task task = findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found : " + id));
        task.setTitle(title);
        task.setDescription(description);
    }

    @Override
    public void addFilePath(Long id, MultipartFile file, String filePath) {
        Task task = findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found : " + id));
        boolean isUnique = !task.getFiles().contains(filePath);
        if (isUnique) {
            task.getFiles().add(filePath);
        }
    }

    private Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}