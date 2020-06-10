package pl.maltoza.imports.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tags.entity.Tag;

import java.util.Set;

@Data
@AllArgsConstructor
public class ImportTask {
    private Long projectId;
    private String name;
    private String description;
    private Set<String> tags;
}
