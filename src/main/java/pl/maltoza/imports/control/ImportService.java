package pl.maltoza.imports.control;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.maltoza.imports.boundary.ImportProject;
import pl.maltoza.imports.boundary.ImportTask;
import pl.maltoza.projects.control.ProjectService;
import pl.maltoza.projects.entity.Project;
import pl.maltoza.tasks.control.TasksService;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ImportService {

    private final TasksService tasksService;
    private final ProjectService projectService;

    @Transactional
    public void startImport(List<ImportTask> tasks, List<ImportProject> projects) {
        log.info("Importing {} projects, {} tasks", projects.size(), tasks.size());
        for (ImportProject project : projects) {
            log.info("Importing project {}", project.getName());
            projectService.save(new Project(project.getName()));
            tasks.stream()
                    .filter(t -> t.getProjectId().equals(project.getId()))
                    .forEach(t -> tasksService.addTask(t.getName(), t.getDescription(), t.getTags()));

        }
    }
}
