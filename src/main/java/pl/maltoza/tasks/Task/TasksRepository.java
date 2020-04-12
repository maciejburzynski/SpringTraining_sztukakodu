package pl.maltoza.tasks.Task;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> fetchAll();
}
