package pl.maltoza.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.maltoza.tasks.boundary.FileStorageService;
import pl.maltoza.tasks.boundary.StorageService;

import java.nio.file.Path;

@Slf4j
@Configuration
public class TasktreeConfig {
    StorageConfig storageConfig;

    @Autowired
    public TasktreeConfig(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Bean
    public Clock clock() {
        log.info("Registering clock bean");
        return new SystemClock();
    }
    @Bean
    public StorageService storageService() {
        log.info("Registering Storage service as a bean");
        return new FileStorageService(Path.of(storageConfig.getPathToAddFile()));
    }

}

