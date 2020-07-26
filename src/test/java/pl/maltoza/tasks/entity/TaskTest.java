package pl.maltoza.tasks.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.maltoza.tags.entity.Tag;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void shouldAddTag() {
//        given
        Task task = new Task();
        Tag pilne = new Tag("pilne");

//        when

        task.addTag(pilne);

//        then
        assertTrue(task.getTags().contains(pilne));
    }
}