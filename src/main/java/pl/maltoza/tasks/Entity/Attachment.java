package pl.maltoza.tasks.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.Table;

@Data
@Table(name = "attachment")
@AllArgsConstructor
public class Attachment {
    private String filename;
    private String comment;
}
