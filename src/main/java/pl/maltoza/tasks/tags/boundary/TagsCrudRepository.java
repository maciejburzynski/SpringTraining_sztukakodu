package pl.maltoza.tasks.tags.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maltoza.tasks.tags.entity.Tag;

public interface TagsCrudRepository extends JpaRepository<Tag, Long> {
}
