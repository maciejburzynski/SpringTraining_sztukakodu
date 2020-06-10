package pl.maltoza.tasks.boundary;

import lombok.Data;

import java.util.Set;

@Data
public class CreateTaskRequest {
    String title;
    String description;
    String attachmentComment;
    Set <String> tags;
}
