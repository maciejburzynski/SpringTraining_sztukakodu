package pl.maltoza.tasks.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import pl.maltoza.tags.control.TagsService;
import pl.maltoza.tasks.entity.Task;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TasksServiceIntegrationTest {

    @Autowired
    TasksService tasksService;

    @Autowired
    TagsService tagsService;

    @Test
    public void optimisticLockingTest() {
        //given
        Task task = tasksService.addTask("Kupic lodowke", "Pod zabaaauaaaaaadowe", tags());

        //when
        Task findOne = tasksService.findById(task.getId());
        Task findOther = tasksService.findById(task.getId());

        findOne.setTitle("KUPIC LODOWKE");
        findOther.setTitle("SPRZEDAC LODOWKE");

        tasksService.save(findOne);

        //then
        assertThatThrownBy(() -> tasksService.save(findOther))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

    private HashSet<String> tags(String... tags) {
        return new HashSet<>(asList(tags));
    }
}