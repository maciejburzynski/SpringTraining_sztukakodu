package pl.maltoza.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.maltoza.projects.boundary.ProjectRepository;
import pl.maltoza.projects.entity.Project;
import pl.maltoza.tasks.boundary.TasksRepository;
import pl.maltoza.tasks.entity.Task;
import pl.maltoza.tags.boundary.TagsRepository;
import pl.maltoza.tags.entity.Tag;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Component
@AllArgsConstructor
@Slf4j
public class DevStartup {

    private final TasksRepository tasksRepository;
    private final TagsRepository tagsRepository;
    private final Clock clock;
    private final ProjectRepository projectRepository;



    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeApplication() {
        log.info("initializing");
        insertTask();
        insertTag();
        insertProjectOfJava();
        insertProjectOfHome();
        log.info("initialized");
    }

    private void insertProjectOfJava() {
        Project project = new Project("Zostać Java Developerem");
        Task webinarTask = new Task("Ogarnąć Jave", "ogarnąć wyjątki", clock.time());
        tagsRepository.findByNameContainingIgnoreCase("W domu").ifPresent(tag -> webinarTask.addTag(tag));
        List<Task> tasks = Arrays.asList(
                new Task("task project 1","description 1", clock.time()),
                new Task("task project 2","description 2", clock.time()),
                new Task("task project 3","description 3", clock.time()),
                webinarTask
        );
        project.addTasks(tasks);
        projectRepository.save(project);
        log.info("Added {} tasks to java project", tasks.size());
    }
    private void insertProjectOfHome() {
        Project project = new Project("Zostać kredytobiorcą");
        Task webinarTask = new Task("Ogarnąć Jave", "ogarnąć wyjątki", clock.time());
        List<Task> tasks = Arrays.asList(
                new Task("task project 1","description 1", clock.time()),
                new Task("task project 2","description 2", clock.time()),
                new Task("task project 3","description 3", clock.time()),
                webinarTask
        );
        tagsRepository.findByNameContainingIgnoreCase("W pracy")
                .ifPresent(tag -> {
                    tasks.forEach(task -> task.addTag(tag));
                });
        project.addTasks(tasks);
        projectRepository.save(project);
        log.info("Added {} tasks to home project", tasks.size());
    }

    private void insertTask() {
        List<Task> tasks = Arrays.asList(
                new Task("Title 1", "desc 1", clock.time()),
                new Task("Title 2", "desc 2", clock.time()),
                new Task("Title 3", "desc 3", clock.time())
        );
        tasksRepository.addAll(tasks);
        log.info("added tasks");
    }

    private void insertTag() {
        List<Tag> tags = Arrays.asList(
                new Tag("pilne"),
                new Tag("w domu"),
                new Tag("na mieście")
        );
        tagsRepository.saveAll(tags);
        log.info("added tags");

    }
}
