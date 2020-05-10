package pl.maltoza.tasks.tags.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tasks.tags.boundary.TagsCrudRepository;
import pl.maltoza.tasks.tags.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TagsService {
    private final TagsCrudRepository tagsRepository;

    @Autowired
    public TagsService(TagsCrudRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public Tag findById(Long id){
      return tagsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id: " + id));
    }

    public Set<Tag> findAllById(List<Long> tagIds) {
        return StreamSupport.stream(
                tagsRepository.findAllById(tagIds)
                        .spliterator(),
                false)
                .collect(Collectors.toSet());
    }
}
