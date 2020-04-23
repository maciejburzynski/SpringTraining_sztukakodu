package pl.maltoza.tasks;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pl.maltoza.tasks.Boundary.FileStorageService;
import pl.maltoza.tasks.Boundary.StorageService;

import java.nio.file.Path;


@Data
@Component
@ConfigurationProperties(prefix = "app.tasks")
public class StorageConfig {
    private Path addFilePath;
}
