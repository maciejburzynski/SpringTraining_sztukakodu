package pl.maltoza.tasks.Entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import pl.maltoza.tasks.tags.entity.Tag;

@Data
@Table("tag_task")
public class TagRef {
    private Long tag;

    public TagRef() {
    }

    public TagRef(Tag tag) {
        this.tag= tag.getId();
    }
}
