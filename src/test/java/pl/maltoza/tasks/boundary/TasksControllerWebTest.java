package pl.maltoza.tasks.boundary;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.maltoza.tasks.confTraining.TasksConfig;
import pl.maltoza.tasks.control.TasksService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TasksController.class)
@Import(TasksConfig.class)
class TasksControllerWebTest {

    @MockBean
    TasksService tasksService;

    @MockBean
    TasksRepository tasksRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldAddTask() throws Exception {
//        given
        JSONObject jsonObject = new JSONObject()
                .put("title", "Kupić kawę")
                .put("description", "Kupić nespresso");
//        when

//        then
        mockMvc.perform(
                post("/restapi/tasks")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)


        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }


}