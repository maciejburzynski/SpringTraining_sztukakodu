package pl.maltoza.tasks.Boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.TagRef;
import pl.maltoza.tasks.Entity.Task;
import pl.maltoza.tasks.tags.control.TagsService;
import pl.maltoza.tasks.tags.entity.Tag;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

@RestController
@Slf4j
@RequestMapping(value = ("/restapi/tasks"))
@RequiredArgsConstructor

public class TasksController {
    private final Logger logger = LoggerFactory.getLogger(TasksController.class);
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;
    private final TagsService tagsService;

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional<String> title) {
        logger.info("Fetching all tasks time ...");
        return toTaskResponse(title.map(tasksService::filterAllByTitle)
                .orElseGet(tasksService::fetchAll));
    }

    @GetMapping(path = "/_search")
    public List<TaskResponse> searchTask(@RequestParam(defaultValue = "false") Boolean attachments) {
        if (attachments) {
            return toTaskResponse(tasksService.findWithAttachments());
        } else {
            return toTaskResponse(tasksService.fetchAll());
        }
    }


    @PostMapping(path = "/{id}/tags")
    public ResponseEntity addTag(@PathVariable Long id, @RequestBody AddTagRequest request) {
        tasksService.addTag(id, request.tagId);
        return ResponseEntity
                .ok()
                .build();

    }

    @DeleteMapping(path = "/{id}/tags/{tagId}")
    public ResponseEntity removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        tasksService.removeTag(id, tagId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Fetching task with id: {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request)
            throws IOException {
        Optional<Resource> attachment = tasksService.loadAttachment(id, filename);
        if (attachment.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        String mimeType;
        Resource resource;

        logger.info("Fetching file: {} time...", filename);
        tasksRepository.fetchById(id);
        resource = attachment.get();
        mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        if (tasksRepository.fetchById(id).getAttachments().isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.parseMediaType(mimeType))
                    .build();
        else {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        }
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id,
                                        @RequestParam("comment") String comment,
                                        @RequestParam("file") MultipartFile file)
            throws IOException {
        tasksService.addTaskAttachment(id, file, comment);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }


    @PostMapping
    public ResponseEntity addTask(@RequestBody CreateTaskRequest task) {
        logger.info("Adding time...");
        tasksService.addTask(task.getTitle(), task.getDescription());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        logger.info("Deleting task no. {} time...", id);
        tasksRepository.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(HttpServletResponse response, @PathVariable Long
            id, @RequestBody UpdateTaskRequest request) {
        tasksService.updateTask(id, request.title, request.description);
        logger.info("Updating task...");
        return ResponseEntity
                .noContent()
                .build();
    }

    private List<TaskResponse> toTaskResponse(List<Task> task) {
        return task.stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

//    private TaskResponse toTaskResponse(Task task) {
//        List<Long> tagIds = task.getTagRefs().stream().map(TagRef::getTag).collect(toList());
//        Set<Tag> tags = tagsService.findAllById(tagIds);
//        return TaskResponse.from(task, tags);
//    }
    private TaskResponse toTaskResponse(Task task) {
        List<Long> tagIds = task.getTagRefs().stream().map(TagRef::getTag).collect(toList());
        Set<Tag> tags = tagsService.findAllById(tagIds);
        return TaskResponse.from(task, tags);
    }
}

