package pl.maltoza.tasks.boundary;

import pl.maltoza.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> fetchAll();

    void addAll(Iterable<Task> taskIterable);
    Task fetchById(Long id);

    void deleteById(Long id);

    void update(Long id, String title, String description);

    void save(Task task);

    List<Task> findByTitle(String title);

    List<Task> findWithAttachments();

}
