package pl.maltoza.tasks;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.maltoza.tasks.Boundary.TasksRepository;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;
import pl.maltoza.tasks.tags.boundary.TagsRepository;
import pl.maltoza.tasks.tags.entity.Tag;

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

    @EventListener(ApplicationReadyEvent.class)
    public void initializeApplication() {
        log.info("initializing");
        insertTask();
        insertTag();
        log.info("initialized");
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
