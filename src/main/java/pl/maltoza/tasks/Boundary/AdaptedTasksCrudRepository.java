package pl.maltoza.tasks.Boundary;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tasks.Entity.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Primary
public class AdaptedTasksCrudRepository implements TasksRepository {
    private final TasksCrudRepository tasksCrudRepository;

    public AdaptedTasksCrudRepository(TasksCrudRepository tasksCrudRepository) {
        this.tasksCrudRepository = tasksCrudRepository;
    }

    @Override
    public void add(Task task) {
        tasksCrudRepository.save(task);
    }

    @Override
    public List<Task> fetchAll() {
        return StreamSupport
                .stream(tasksCrudRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Task fetchById(Long id) {
        return tasksCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Tasks not found with this id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        tasksCrudRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String title, String description) {
        tasksCrudRepository.updateTitleDescription(id, title, description);
    }


    @Override
    public void save(Task task) {
        tasksCrudRepository.save(task);
    }

    @Override
    public List<Task> findByTitle(String title) {
        return tasksCrudRepository.findAllByTitleLike(title);
    }

    @Override
    public List<Task> findWithAttachments() {
        return Collections.emptyList();
//        return tasksCrudRepository.findWithAttachments();
    }
}
