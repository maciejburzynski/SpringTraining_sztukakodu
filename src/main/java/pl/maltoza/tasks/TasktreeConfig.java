package pl.maltoza.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TasktreeConfig {

    @Bean
    public Clock clock(){
        log.info("Registering clock bean");
        return new SystemClock();
    }
}
