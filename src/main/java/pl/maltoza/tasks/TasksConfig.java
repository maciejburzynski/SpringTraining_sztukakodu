package pl.maltoza.tasks;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

//@Component
@Data
//@ConfigurationProperties(prefix = "app.tasks")
public class TasksConfig {
private String endpointMessage;
}
