package pl.maltoza.tasks.Entity;

import lombok.*;
import pl.maltoza.tasks.tags.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "task")
@NoArgsConstructor
@NamedEntityGraph(
        name = "task.detail",
        attributeNodes = {
                @NamedAttributeNode("attachments"),
                @NamedAttributeNode("tags")
        }
)
public class Task extends BaseEntity {

    private String title;
    private String description;
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task")
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tag_task",
            joinColumns =
            @JoinColumn(name = "task"),
            inverseJoinColumns =
            @JoinColumn(name = "tag"))
    private Set<Tag> tags = new HashSet<>();

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
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }
}
