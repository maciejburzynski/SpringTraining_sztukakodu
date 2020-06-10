package pl.maltoza.projects.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.maltoza.tasks.entity.BaseEntity;
import pl.maltoza.tasks.entity.Task;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@NamedEntityGraph(
        name = "Project.detail",
        attributeNodes = {
                @NamedAttributeNode(
                        value = "tasks",
                        subgraph = "Task.detail"
                )
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "Task.detail",
                        attributeNodes = {
                                @NamedAttributeNode("attachments"),
                                @NamedAttributeNode("tags")
                        }
                )
        }
)
public class Project extends BaseEntity {

    String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    Set<Task> tasks = new HashSet<>();

    public Project(String title) {
        this.title = title;
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}
