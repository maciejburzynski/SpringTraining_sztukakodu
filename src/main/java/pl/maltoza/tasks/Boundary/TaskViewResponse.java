package pl.maltoza.tasks.Boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.Entity.Task;
import pl.maltoza.tasks.tags.entity.Tag;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
public class TaskViewResponse {
    long id;
    String title;
    String description;
    LocalDateTime createdAt;

    static TaskViewResponse from(Task task) {
        return new TaskViewResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt()

        );
    }

}

