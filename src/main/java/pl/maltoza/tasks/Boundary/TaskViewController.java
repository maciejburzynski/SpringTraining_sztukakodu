package pl.maltoza.tasks.Boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.maltoza.tasks.Control.TasksService;

@Controller
public class TaskViewController {

    private final TasksService tasksService;

    @Autowired
    TaskViewController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        return "home";
    }


}
