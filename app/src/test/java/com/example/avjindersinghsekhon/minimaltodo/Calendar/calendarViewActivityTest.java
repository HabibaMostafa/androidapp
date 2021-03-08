package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import junit.framework.TestCase;
import com.example.avjindersinghsekhon.minimaltodo.Calendar.calendarViewActivity;

import org.junit.Test;

public class calendarViewActivityTest extends TestCase {
    calendarViewActivity calenderView = new calendarViewActivity();

    @Test
    public void testConvertDate() {

        assertEquals( "Date Selected: January 1, 2021", calenderView.convertDate(1,0,2021));

        assertEquals( "Date Selected: September 23, 1970", calenderView.convertDate(23,8,1970));

        assertEquals( "Date Selected: March 8, 2000", calenderView.convertDate(8,2,2000));

        assertEquals( "Date Selected: July 1, 2015", calenderView.convertDate(1,6,2015));

        assertEquals( "Date Selected: March 8, 2000", calenderView.convertDate(8,2,2000));

        assertEquals( "Date Selected: November 4, 1997", calenderView.convertDate(4,10,1997));

        return;
    }

}