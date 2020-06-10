package pl.maltoza.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.entity.Task;
import pl.maltoza.tags.entity.Tag;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
public class TaskResponse {
    long id;
    String uuid;
    String title;
    String description;
    LocalDateTime createdAt;
    Set<AttachmentResponse> attachments;
    Set<TagResponse> tag;

    static TaskResponse from(Task task, Set<Tag>tags) {
        return new TaskResponse(
                task.getId(),
                task.getUuid(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getAttachments()
                        .stream()
                        .map(AttachmentResponse::from)
                        .collect(toSet()),
                tags.stream()
                .map(it -> new TagResponse(it))
                .collect(Collectors.toSet())
        );
    }

}

