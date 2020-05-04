package pl.maltoza.tasks.Boundary;

import org.springframework.data.repository.CrudRepository;
import pl.maltoza.tasks.Entity.Task;

public interface TasksCrudRepository extends CrudRepository<Task, Long> {


}
