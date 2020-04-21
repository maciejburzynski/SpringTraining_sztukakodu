package pl.maltoza.tasks.Boundary;

import pl.maltoza.tasks.Entity.Task;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> fetchAll();


    Task fetchById(Long id);

    void deleteById(Long id);

    void update(Long id, String title, String description);
}
