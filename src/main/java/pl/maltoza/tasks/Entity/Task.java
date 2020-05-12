package pl.maltoza.tasks.Entity;

import lombok.*;
import pl.maltoza.tasks.tags.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private transient Set<Attachment> attachments = new HashSet<>();
    private transient Set<TagRef> tagRefs = new HashSet<>();

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

    public void addTag(Tag tag) {
        tagRefs.add(new TagRef(tag));
    }

    public void removeTag(Tag tag) {
        tagRefs.remove(new TagRef(tag));
    }
}
