package com.example.avjindersinghsekhon.minimaltodo.Utility;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;

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
}