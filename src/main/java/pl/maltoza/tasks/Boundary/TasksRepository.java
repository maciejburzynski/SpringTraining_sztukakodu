package pl.maltoza.tasks.Boundary;

import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.tasks.Entity.Task;

import java.util.List;
import java.util.Optional;

public interface TasksRepository {
    void add(Task task);
    List<Task> fetchAll();


    Task fetchById(Long id);

    void deleteById(Long id);

    void update(Long id, String title, String description);

    void save(Task task);

    List<Task> findByTitle(String title);

    List<Task> findWithAttachments();

}
