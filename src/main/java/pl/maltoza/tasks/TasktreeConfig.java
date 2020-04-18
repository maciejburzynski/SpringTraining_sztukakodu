package pl.maltoza.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.maltoza.tasks.Boundary.FileStorageService;
import pl.maltoza.tasks.Boundary.StorageService;

import java.nio.file.Path;

@Slf4j
@Configuration
public class TasktreeConfig {

    @Bean
    public Clock clock() {
        log.info("Registering clock bean");
        return new SystemClock();
    }

    @Bean
    public StorageService storageService() {
        log.info("Registering Storage service as a bean");
        return new FileStorageService(Path.of("/Users/maciejburzynski/Desktop/testDir"));
    }
}

