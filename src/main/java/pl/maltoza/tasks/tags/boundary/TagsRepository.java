package pl.maltoza.tasks.tags.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maltoza.tasks.tags.entity.Tag;

public interface TagsRepository extends JpaRepository<Tag, Long> {
}
