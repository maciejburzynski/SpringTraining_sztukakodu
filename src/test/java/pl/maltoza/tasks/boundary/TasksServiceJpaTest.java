package pl.maltoza.tasks.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.maltoza.tags.control.TagsService;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.SystemClock;
import pl.maltoza.tasks.boundary.AdaptedTasksCrudRepository;
import pl.maltoza.tasks.boundary.StorageService;
import pl.maltoza.tasks.boundary.TasksCrudRepository;
import pl.maltoza.tasks.boundary.TasksRepository;
import pl.maltoza.tasks.entity.Task;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class TasksServiceJpaTest {

    @Autowired
    TasksCrudRepository tasksCrudRepository;

    Clock clock = new SystemClock();

    @Test
    public void shouldReturnPriorityTasks(){

//        given
        Task t1 = new Task();
        t1.setPriority(true);
        Task t2 = new Task();
        t2.setPriority(false);
        Task t3 = new Task();
        t3.setDueDate(clock.date().minusDays(1));
        Task t4 = new Task();
        t4.setDueDate(clock.date());
        Task t5 = new Task();
        t5.setDueDate(clock.date().plusDays(1));
        tasksCrudRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
//          when
        List<Task> tasks = tasksCrudRepository.findByPriorityIsTrue();

//          then
        assertThat(tasks)
                .hasSize(1)
                .containsExactlyInAnyOrder(t1);
    }

    @Test
    public void shouldReturnDueTasks(){

//        given
        Task t1 = new Task();
        t1.setPriority(true);
        Task t2 = new Task();
        t2.setPriority(false);
        Task t3 = new Task();
        t3.setDueDate(clock.date().minusDays(1));
        Task t4 = new Task();
        t4.setDueDate(clock.date());
        Task t5 = new Task();
        t5.setDueDate(clock.date().plusDays(1));
        tasksCrudRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
//          when
        List<Task> tasks = tasksCrudRepository.findByDueDateIsLessThanEqual(clock.date());

//          then
        assertThat(tasks)
                .hasSize(2)
                .containsExactlyInAnyOrder(t3, t4);
    }


}