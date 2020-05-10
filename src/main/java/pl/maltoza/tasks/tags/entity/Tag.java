package pl.maltoza.tasks.tags.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Table("tag")
public class Tag {
    @Id
    private Long id;
    private String Name;
}
