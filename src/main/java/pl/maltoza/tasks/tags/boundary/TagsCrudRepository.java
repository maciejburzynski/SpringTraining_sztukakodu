package pl.maltoza.tasks.tags.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.maltoza.tasks.tags.entity.Tag;

public interface TagsCrudRepository extends CrudRepository<Tag, Long> {
}
