package pl.maltoza.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions.*;

class SystemClockTest {


    Clock clock = new SystemClock();

    @Test
    public void shouldReturnCurrentTime(){
//      given
        LocalDateTime now = LocalDateTime.now();

//        when
        LocalDateTime time = clock.time();

//        then
        Assertions.assertEquals(now.getYear(), time.getYear(), "Something is wrong with year");


    }
}