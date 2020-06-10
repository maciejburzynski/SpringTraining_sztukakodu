package pl.maltoza.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.entity.Task;

import java.time.LocalDateTime;

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

