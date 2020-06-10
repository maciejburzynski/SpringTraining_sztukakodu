package pl.maltoza.projects.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maltoza.projects.boundary.ProjectRepository;
import pl.maltoza.projects.boundary.ProjectView;
import pl.maltoza.projects.entity.Project;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

     private final ProjectRepository projectRepository;

     public List<Project> findAll(){
         return projectRepository.findAll();
     }

     public List<ProjectView> findAllProjectedBy(){
         return projectRepository.findAllProjectedBy();
    }


    public Project save(Project project) {
         return projectRepository.save(project);
    }
}
