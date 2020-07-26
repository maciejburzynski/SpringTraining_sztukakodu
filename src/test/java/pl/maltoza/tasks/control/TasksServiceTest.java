package pl.maltoza.tasks.control;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.maltoza.tags.control.TagsService;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.SystemClock;
import pl.maltoza.tasks.boundary.StorageService;
import pl.maltoza.tasks.boundary.TasksRepository;
import pl.maltoza.tasks.entity.Task;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceTest {

    TasksRepository tasksRepository;
    TasksService tasksService;
    Clock clock = new SystemClock();

    @BeforeEach
    public void setup(){
        tasksRepository = mock(TasksRepository.class);
        tasksService = new TasksService(
                tasksRepository,
                mock(StorageService.class),
                mock(TagsService.class),
                clock
                );
    }

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
        when(tasksRepository.findPriorityTasks())
                .thenReturn(Arrays.asList(t1));
        when(tasksRepository.findDueTasks(any()))
                .thenReturn(Arrays.asList(t3, t4));

        //        when

        List<Task> tasks = tasksService.priorityTask();
//        then
        assertThat(tasks)
                .containsExactlyInAnyOrder(t1, t3, t4);
    }

}