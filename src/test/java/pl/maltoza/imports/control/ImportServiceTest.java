package pl.maltoza.imports.control;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import pl.maltoza.imports.boundary.ImportProject;
import pl.maltoza.imports.boundary.ImportTask;
import pl.maltoza.projects.control.ProjectService;
import pl.maltoza.tags.control.TagsService;
import pl.maltoza.tasks.control.TasksService;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@Slf4j
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Tag("A")
public class ImportServiceTest {

    @Autowired
    TagsService tagsService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TasksService tasksService;

    @Autowired
    ImportService importService;

    @Test
    public void shouldImportEntities() {
        // given
        long kursPid = 1L;
        ImportProject p1 = new ImportProject(kursPid, "szkoła Springa");
        ImportTask t1 = new ImportTask(kursPid, "nagrać moduł 10", "lekcja 4", tags("pilne"));
//        ImportTask t2 = new ImportTask(kursPid, "Odpisać", "na coś", tags());

        long agdPid = 2L;
        ImportProject p2 = new ImportProject(agdPid, "Remont mieszkania");
        ImportTask t3 = new ImportTask(agdPid, "kupić lodówkę", "lekcja 4", tags("pilne", "niepilne"));
//        ImportTask t4 = new ImportTask(agdPid, "kupić farby", "na coś", tags("pilne"));
//        ImportTask poison = new ImportTask(null, "Odpisać", "na coś", tags("pilne"));

        try {
            importService.startImport(Arrays.asList(t1), Arrays.asList(p1));
        } catch (Exception e) {
            log.error("Something is wrong", e);
        }

        Assertions.assertThat(tasksService.fetchAll().size()).isEqualTo(1);
        Assertions.assertThat(projectService.findAll().size()).isEqualTo(1);
//        Assertions.assertThat(tagsService.findAll().size()).isEqualTo(4);

    }


    private HashSet<String> tags(String... tags) {
        return new HashSet<>(Arrays.asList(tags));
    }
}