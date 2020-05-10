package pl.maltoza.tasks.Boundary;

import lombok.Data;

@Data
public class CreateTaskRequest {
    String title;
    String description;
    String attachmentComment;
}
