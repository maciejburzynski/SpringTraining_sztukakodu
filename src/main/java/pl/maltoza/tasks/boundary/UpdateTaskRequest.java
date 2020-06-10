package pl.maltoza.tasks.boundary;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    String title;
    String description;
}
