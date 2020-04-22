package pl.maltoza.tasks.Boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createdAt;
    List<String> files;

}

