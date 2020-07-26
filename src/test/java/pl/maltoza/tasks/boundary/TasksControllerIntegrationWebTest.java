package pl.maltoza.tasks.boundary;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TasksControllerIntegrationWebTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldAddTask() throws Exception {

        //        given

        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("kupić kawe");
        request.setDescription("Kapsulki nespresso");

        //        when

        ResponseEntity<String> response = testRestTemplate
                .postForEntity("/restapi/tasks", request, String.class);

        ResponseEntity<TaskResponse[]> tasksListsResponse = testRestTemplate
                .getForEntity("/restapi/tasks", TaskResponse[].class);

        //        then
        Assertions.assertThat(response
                .getStatusCode()
                .toString())
                .isEqualTo("201 CREATED");

        Assertions.assertThat(tasksListsResponse
                .getBody())
                .hasSize(1);

        Assertions.assertThat(tasksListsResponse
                .getBody()[0].title)
                .isEqualTo("kupić kawe");

    }
}