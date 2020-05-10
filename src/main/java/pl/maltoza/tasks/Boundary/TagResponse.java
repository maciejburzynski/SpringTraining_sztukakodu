package pl.maltoza.tasks.Boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maltoza.tasks.tags.entity.Tag;

@Data
@AllArgsConstructor
public class TagResponse {
    Long id;
    String name;

    public TagResponse(Tag it) {
        this.id = it.getId();
        this.name = it.getName();
    }
}
