package pl.maltoza.tasks;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Primary
    @Bean
    public Clock testClock(){
        return new FakeClock();
    }
}
