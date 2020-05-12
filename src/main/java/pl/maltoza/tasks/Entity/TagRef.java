package pl.maltoza.tasks.Entity;

import lombok.Data;
import pl.maltoza.tasks.tags.entity.Tag;

import javax.persistence.Table;

@Data
@Table(name ="tag_task")
public class TagRef {
    private Long tag;

    public TagRef() {
    }

    public TagRef(Tag tag) {
        this.tag= tag.getId();
    }
}
