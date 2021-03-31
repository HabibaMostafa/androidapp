package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import junit.framework.TestCase;
import com.example.avjindersinghsekhon.minimaltodo.Calendar.calendarViewActivity;

import org.junit.Test;

public class calendarViewActivityTest extends TestCase {
    calendarViewActivity calendarView = new calendarViewActivity();

    @Test
    public void testConvertDate() {

        assertEquals( "Date Selected: January 1, 2021", calendarView.convertDate(1,0,2021));

        assertEquals( "Date Selected: September 23, 1970", calendarView.convertDate(23,8,1970));

        assertEquals( "Date Selected: March 8, 2000", calendarView.convertDate(8,2,2000));

        assertEquals( "Date Selected: July 1, 2015", calendarView.convertDate(1,6,2015));

        assertEquals( "Date Selected: March 8, 2000", calendarView.convertDate(8,2,2000));

        assertEquals( "Date Selected: November 4, 1997", calendarView.convertDate(4,10,1997));

        return;
    }

    @Test
    public void testConvertDateMatch() {

        assertEquals( "Fri Jan 01 00:00:00 EST 2021", calendarView.convertDateMatch(1,0,2021));

        assertEquals( "Wed Sep 23 00:00:00 EDT 1970", calendarView.convertDateMatch(23,8,1970));

        assertEquals( "Wed Mar 08 00:00:00 EST 2000", calendarView.convertDateMatch(8,2,2000));

        assertEquals( "Wed Jul 01 00:00:00 EDT 2015", calendarView.convertDateMatch(1,6,2015));

        assertEquals( "Wed Mar 08 00:00:00 EST 2000", calendarView.convertDateMatch(8,2,2000));

        assertEquals( "Tue Nov 04 00:00:00 EST 1997", calendarView.convertDateMatch(4,10,1997));

        return;
    }

}