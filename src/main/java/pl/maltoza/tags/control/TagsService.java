package pl.maltoza.tags.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maltoza.exceptions.NotFoundException;
import pl.maltoza.tags.boundary.TagsRepository;
import pl.maltoza.tags.entity.Tag;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TagsService {
    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
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
    public Optional<Tag> findByName(String name){
        return tagsRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Tag> findAll() {
        return tagsRepository.findAll();
    }
}
