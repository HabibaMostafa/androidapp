package com.example.avjindersinghsekhon.minimaltodo.Utility;

public class CalendarDate {
    public static String selectedDate = "noData";
    public static boolean dateChanged = false;

    public static void setSelectedDate(String sDate) {
        selectedDate = sDate;
    }

    public static String getSelectedDate() {
        return selectedDate;
    }

    public static void setDateChanged(boolean dateChanged) {
        CalendarDate.dateChanged = dateChanged;
    }
}
