package pl.maltoza.tasks;

import java.time.LocalDateTime;

public class FakeClock implements Clock {

    private LocalDateTime time;

    public FakeClock() {
        this(LocalDateTime.now());
    }

    public FakeClock(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime time() {
        return time;
    }
}
