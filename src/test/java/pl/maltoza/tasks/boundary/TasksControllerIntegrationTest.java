package pl.maltoza.tasks.boundary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.maltoza.tasks.Clock;
import pl.maltoza.tasks.TestConfig;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@TestPropertySource(
        locations = {
                "classpath:customowe.yml"
        }
//        properties = "app.tasks.endpointMessage: Hello Worldddddddd from Configg"
)
@Import(TestConfig.class)
class TasksControllerIntegrationTest {

    @Autowired
    TasksController tasksController;

    @Autowired
    Clock clock;


    @Test
    public void shouldDisplayHelloMessege() {

//        when
        String hello = tasksController.hello();

//        expect
        Assertions.assertThat(hello).isEqualTo("Hello Worldddddddd from Configg");
    }

    @Test
    public void shouldAddTimeCreatedToTask() {

        //            given
        CreateTaskRequest request = new CreateTaskRequest();

        //            when
        tasksController.addTask(request);
        List<TaskResponse> tasks = tasksController.getTasks(Optional.empty());
//        expect
        Assertions.assertThat(tasks)
                .hasSize(1)
                .extracting(g -> g.getCreatedAt())
                .first()
                .isEqualTo(clock.time());

    }
}