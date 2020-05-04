package pl.maltoza.tasks.Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.tasks.Boundary.StorageService;
import pl.maltoza.tasks.Boundary.TasksRepository;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.Entity.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final StorageService storageService;
    private final Clock clock;

    @Autowired
    public TasksService(TasksRepository tasksRepository, StorageService storageService, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.storageService = storageService;
        this.clock = clock;
    }

    public Task addTask(String title, String description) {
        Task task = new Task(
                title,
                description,
                clock.time());
        tasksRepository.add(task);
        return task;
    }

    public void addTaskAttachment(Long taskId, MultipartFile attachment) throws IOException {
        Task task = tasksRepository.fetchById(taskId);
        if (!attachment.isEmpty()) {
            String filename = storageService.saveFile(taskId, attachment);
            task.addAttachment(filename);
        }
        tasksRepository.save(task);
    }


    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }

    public List<Task> fetchAll() {
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query) {
        return tasksRepository.fetchAll()
                .stream()
                .filter(task -> {
                    return task.getTitle().toUpperCase().contains(query.toUpperCase()) ||
                            task.getDescription().toUpperCase().contains(query.toUpperCase());
                })
                .collect(toList());
    }

    public void removeTask(Long id) {
        tasksRepository.deleteById(id);
    }

    public Optional<Resource> loadAttachment(Long id, String filename) throws MalformedURLException {
        Optional<Resource> attachment = Optional.empty();
        Task task = tasksRepository.fetchById(id);
        if (task.getAttachments().contains(filename)) {
            attachment = Optional.of(storageService.loadFile(filename));
        }
        return attachment;
    }
}
