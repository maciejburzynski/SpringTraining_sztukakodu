package pl.maltoza.tasks.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.maltoza.tags.entity.Tag;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task extends BaseEntity {

    private String title;
    private String description;
    private boolean priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "tag_task",
            joinColumns =
            @JoinColumn(name = "task"),
            inverseJoinColumns =
            @JoinColumn(name = "tag"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    public void addTags(Collection <Tag> tags) {
        this.tags.addAll(tags);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void markPriority(boolean priority){
        this.priority = priority;
    }

    public void setDueDate(LocalDate date){
        this.dueDate = date;
    }
}
