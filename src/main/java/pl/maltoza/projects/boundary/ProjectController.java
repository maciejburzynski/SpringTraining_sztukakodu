package pl.maltoza.projects.boundary;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.maltoza.projects.control.ProjectService;

import java.util.List;

@RestController
@RequestMapping(path = "/restapi/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity getAll(@RequestParam (defaultValue = "false") Boolean full){
        List projects = full ? projectService.findAll() : projectService.findAllProjectedBy();
        return ResponseEntity.ok(projects);
    }
}
