package pl.maltoza.tasks.Boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.maltoza.tasks.Control.TasksService;
import pl.maltoza.tasks.Entity.Task;

import java.io.IOException;

@Slf4j
@Controller
public class TaskViewController {

    private final TasksService tasksService;
    private final StorageService storageService;

    @Autowired
    TaskViewController(TasksService tasksService, StorageService storageService) {
        this.tasksService = tasksService;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request,
                          @RequestParam("attachment") MultipartFile attachment) throws IOException {
        log.info("adding task to view time...");
        Task task = tasksService.addTask(request.title, request.description);
        if (!attachment.isEmpty()) {
            storageService.saveFile(task.getId(), attachment);
        }
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String removeTask(@PathVariable Long id) {
        tasksService.removeTask(id);
        return "redirect:/";
    }


}
