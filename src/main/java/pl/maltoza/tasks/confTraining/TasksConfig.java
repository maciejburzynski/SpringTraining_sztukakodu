package pl.maltoza.tasks.confTraining;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "app.tasks")
public class TasksConfig {
    private String endpointMessage;
}
