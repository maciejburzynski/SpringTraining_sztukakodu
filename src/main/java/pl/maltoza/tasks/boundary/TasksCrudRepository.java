package pl.maltoza.tasks.boundary;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.maltoza.tasks.entity.Task;

import java.util.List;

public interface TasksCrudRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task SET title= :title, description= :description WHERE id= :id")
    void updateTitleDescription(@Param("id") Long id,
                                @Param("title") String title,
                                @Param("description") String description);

//    @Query("SELECT * FROM Task t WHERE upper(t.title) LIKE '%' || upper(:title) || '%' ")
//    List<Task> findByTitle(@Param("title") String title);

    @Query
    List<Task> findAllByTitleLike(String title);

    @Query("FROM Task t WHERE t.attachments.size > 0")
    List<Task> findWithAttachments();

    @EntityGraph(value = "task.detail",
    type = EntityGraph.EntityGraphType.LOAD)
    List<Task> findAll();

    List<TaskView> findAllBy();

    @Query("FROM Task")
    List<Task> findAllLazy();
}
