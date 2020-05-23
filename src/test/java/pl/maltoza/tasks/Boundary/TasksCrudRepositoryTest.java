package pl.maltoza.tasks.Boundary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.Entity.Task;
import pl.maltoza.tasks.SystemClock;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class TasksCrudRepositoryTest {

    Clock clock = new SystemClock();

    @Autowired
    TasksCrudRepository repository;

    @Test
    public void shouldLoadEntity() {
        // given
        Task task = new Task("Kupić lodówke", "pod zabudowe", clock.time());

        // when
        repository.save(task);
        List<Task> tasks = repository.findAll();

        // then

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getTitle()).isEqualToIgnoringCase("Kupić lodówke");
    }

    @Test
    public void shouldLoadView() {
        // given
        Task task = new Task("Kupić lodówke", "pod zabudowe", clock.time());

        // when
        repository.save(task);
        List<TaskView> tasks = repository.findAllBy();

        // then

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0).getTitle()).isEqualToIgnoringCase("Kupić lodówke");
    }
}
