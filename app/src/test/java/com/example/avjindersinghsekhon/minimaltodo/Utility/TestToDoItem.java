package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.util.Log;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TestToDoItem extends TestCase {

    @Test
    public void testAssignedDateJSON() throws JSONException {
        ToDoItem toDo = new ToDoItem("bodyText", "description", false, null);
        Date theDate = toDo.getAssignedDate();
        String theDateString = toDo.assignedDateToString();

        JSONObject object1 = toDo.toJSON();
        ToDoItem toDo2 = new ToDoItem(object1);

        String theDateString2 = toDo2.assignedDateToString();
        assertEquals(theDateString, theDateString2);

    }

    @Test
    public void testEndlessRecurrenceDays() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.YEAR, 1921);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date date1 = c.getTime();
        item1.setStartDate(date1);
        item1.setRecurring(true);
        item1.setEndless(true);
        item1.setInterval("Day");
        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);

        ArrayList<Date> testDates = new ArrayList<>();

        Date lastDate = date1;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.DATE, 1);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            assertEquals(item2.getRecurringDates().get(i).toString(), testDates.get(i).toString());
        }
    }

    @Test
    public void testEndlessRecurrenceWeeks() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 23);
        c.set(Calendar.YEAR, 2003);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date date1 = c.getTime();
        item1.setStartDate(date1);
        item1.setRecurring(true);
        item1.setEndless(true);
        item1.setInterval("Week");
        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);

        ArrayList<Date> testDates = new ArrayList<>();

        Date lastDate = date1;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.DATE, 7);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            assertEquals(item2.getRecurringDates().get(i).toString(), testDates.get(i).toString());
        }
    }

    @Test
    public void testEndlessRecurrenceTwoWeeks() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 8);
        c.set(Calendar.YEAR, 1900);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date date1 = c.getTime();
        item1.setStartDate(date1);
        item1.setRecurring(true);
        item1.setEndless(true);
        item1.setInterval("Two Weeks");
        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);

        ArrayList<Date> testDates = new ArrayList<>();

        Date lastDate = date1;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.DATE, 14);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            assertEquals(item2.getRecurringDates().get(i).toString(), testDates.get(i).toString());
        }
    }

    @Test
    public void testEndlessRecurrenceMonth() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 9);
        c.set(Calendar.DATE, 8);
        c.set(Calendar.YEAR, 2005);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date date1 = c.getTime();
        item1.setStartDate(date1);
        item1.setRecurring(true);
        item1.setEndless(true);
        item1.setInterval("Month");
        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);

        ArrayList<Date> testDates = new ArrayList<>();

        Date lastDate = date1;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.MONTH, 1);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            assertEquals(item2.getRecurringDates().get(i).toString(), testDates.get(i).toString());
        }
    }

    @Test
    public void testEndlessRecurrenceYear() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 8);
        c.set(Calendar.YEAR, 1900);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date date1 = c.getTime();
        item1.setStartDate(date1);
        item1.setRecurring(true);
        item1.setEndless(true);
        item1.setInterval("Year");

        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);

        ArrayList<Date> testDates = new ArrayList<>();

        Date lastDate = date1;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.YEAR, 1);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            assertEquals(item2.getRecurringDates().get(i).toString(), testDates.get(i).toString());
        }
    }

    @Test
    public void testRecurrenceLimit() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date start = c.getTime();

        item1.setStartDate(start);
        item1.setRecurring(true);
        item1.setHasLimit(true);
        item1.setRecurrenceLimit(15);
        item1.setInterval("Day");



        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);


        ArrayList<Date> testDates = new ArrayList<>();
        Date lastDate = start;
        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.DATE, 1);
            lastDate = cal.getTime();
            testDates.add(lastDate);
        }

        int counter = 0;
        for(Date date : item2.getRecurringDates() ) {
            System.out.println(date);
            assertEquals(date.toString(), testDates.get(counter).toString());
            counter++;
        }
    }

    @Test
    public void testRecurrenceEndDate() throws JSONException {

        ToDoItem item1 = new ToDoItem("test_text", "description", false, null);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);


        Date start = c.getTime();

        c.add(Calendar.YEAR, 40);
        Date end = c.getTime();


        c.add(Calendar.DATE, 1);
        Date check = c.getTime();


        item1.setStartDate(start);
        item1.setEndDate(end);
        item1.setRecurring(true);
        item1.setHasEndDate(true);
        item1.setInterval("Year");

        JSONObject object1 = item1.toJSON();
        ToDoItem item2 = new ToDoItem(object1);
        ArrayList<Date> testDates = new ArrayList<>();
        Date lastDate = start;

        for(int i = 0; i < item2.getRecurringDates().size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastDate);
            cal.add(Calendar.YEAR, 1);
            lastDate = cal.getTime();

            if(lastDate.after(check)) {
                break;
            }

            else {
                testDates.add(lastDate);
            }

        }

        int counter = 0;
        for(Date date : item2.getRecurringDates() ) {
            System.out.println(date);
            assertEquals(date.toString(), testDates.get(counter).toString());
            counter++;
        }
    }
}