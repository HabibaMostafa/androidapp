package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import com.example.avjindersinghsekhon.minimaltodo.Utility.CalendarDate;

import junit.framework.TestCase;

import org.junit.Test;

public class calendarDateTest extends TestCase {
    CalendarDate calendarDate = new CalendarDate();

    @Test
    public void testGetSelectedDate() {
        assertEquals( "noData", CalendarDate.getSelectedDate());

        return;
    }

    public void testSetSelectedDate() {
        calendarDate.setSelectedDate("test1");
        assertEquals( "test1", CalendarDate.getSelectedDate());

        calendarDate.setSelectedDate("");
        assertEquals( "", CalendarDate.getSelectedDate());

        return;
    }

    @Test
    public void testSetDateChanged() {

        assertFalse(calendarDate.dateChanged);

        calendarDate.setDateChanged(true);
        assertTrue(CalendarDate.dateChanged);

        calendarDate.setDateChanged(false);
        assertFalse(calendarDate.dateChanged);

        return;
    }
}
