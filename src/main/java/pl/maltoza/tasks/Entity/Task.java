package pl.maltoza.tasks.Entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Task {
   private Long id;
   private String title;
   private String description;

}
