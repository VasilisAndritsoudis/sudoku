package test.Counter;

import Counter.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalClockTimerTest {

    @Test
    public void getTimeTest(){
        Counter test = new DigitalClockTimer(20, 21);
        assertEquals("20:21", test.getTime());
    }

    @Test
    public void increaseTest(){
        Counter test = new DigitalClockTimer(20, 58);

        test.increase();
        assertEquals("20:59", test.getTime());

        test.increase();
        assertEquals("21:00", test.getTime());
    }

    @Test
    public void decreaseTest(){
        Counter test = new DigitalClockTimer(0, 1);

        test.decrease();
        assertEquals("00:00", test.getTime());

        test.decrease();
        assertEquals("00:00", test.getTime());
    }

    @Test
    public void isOverTest() {
        Counter test = new DigitalClockTimer(0, 1);

        test.decrease();
        assertTrue(test.isOver());
    }

    @Test
    public void isLowerThanTest() {
        Counter test = new DigitalClockTimer(20, 21);

        assertFalse(test.isLowerThan(20, 21));
        test.decrease();
        assertTrue(test.isLowerThan(20, 21));
    }
}
