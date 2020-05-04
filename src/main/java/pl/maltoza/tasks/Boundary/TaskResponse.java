package pl.maltoza.tasks.Boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.Entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createdAt;
    Set<String> attachments;

    static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getAttachments()
        );
    }
}

