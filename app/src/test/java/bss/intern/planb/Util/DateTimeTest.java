package bss.intern.planb.Util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTimeTest {
    DateTime test;

    @Before
    public void init(){
        test = new DateTime(26,7,2019,1,0);
    }

    @Test
    public void timeToString() {
        assertEquals("01:00", test.timeToString());
    }

    @Test
    public void dateToString() {
        assertEquals("Monday, August 26, 2019", test.dateToString());
    }
}