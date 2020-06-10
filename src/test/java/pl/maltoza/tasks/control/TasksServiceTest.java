package pl.maltoza.tasks.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.maltoza.tags.control.TagsService;
import pl.maltoza.tasks.entity.Task;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TasksServiceTest {

    @Autowired
    TasksService tasksService;

    @Autowired
    TagsService tagsService;

    @Test
    public void optimisticLockingTest() {
        //given
        Task task = tasksService.addTask("Kupić lodówke", "Pod zabudowe", tags());

        //when
        Task findOne = tasksService.findById(task.getId());
        Task findOther = tasksService.findById(task.getId());

        findOne.setTitle("Kupić lodówke");
        findOther.setTitle("Sprzedać lodówke");

        tasksService.save(findOne);
        tasksService.save(findOther);

        //then
        Task actual = tasksService.findById(task.getId());
        assertThat(actual.getTitle()).isEqualTo("Sprzedać lodówke");

    }

    private HashSet<String> tags(String... tags) {
        return new HashSet<>(asList(tags));
    }
}