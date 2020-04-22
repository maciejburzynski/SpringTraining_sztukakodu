package pl.maltoza.tasks.Entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
   private  Long id;
   private  String title;
   private  String description;
   private  LocalDateTime createdAt;
   private  List<String> files;
}
