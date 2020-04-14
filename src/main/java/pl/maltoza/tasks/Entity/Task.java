package pl.maltoza.tasks.Entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Task {
   private  Long id;
   private  String title;
   private  String description;
   private  LocalDateTime createdAt;
}
