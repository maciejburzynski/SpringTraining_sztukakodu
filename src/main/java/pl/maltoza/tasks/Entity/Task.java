package pl.maltoza.tasks.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.maltoza.tasks.tags.entity.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Table("task")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Set<Attachment> attachments;
    private Set<TagRef> tagRefs;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public void addAttachment(String filename, String comment) {
        attachments.add(new Attachment(filename, comment));
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void addTag(Tag tag){
        tagRefs.add(new TagRef(tag));
    }
    public void removeTag(Tag tag){
        tagRefs.remove(new TagRef(tag));
    }
}
