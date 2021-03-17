package com.example.avjindersinghsekhon.minimaltodo.Reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class RecurringFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Button doneRecurring;
    private TextView recurringDays;
    private boolean[] selectDay;
    //date-picker
    private EditText mDateEditText;
    private ToDoItem mUserToDoItem;
    private Date endDate;
    private Date mUserReminderDate;
    private String theme;
    private static View theView;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday", "Saturday"};


    public RecurringFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recurring, container, false);

        //Adding a Calendar so user can select date (date picker, issue #51)
        mDateEditText = (EditText) view.findViewById(R.id.endDateRecurrence);
        mUserToDoItem = (ToDoItem) getActivity().getIntent().getSerializableExtra(MainFragment.TODOITEM);

        //When the date textbox gets pressed, open a calendar selector
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                //hideKeyboard(mToDoTextBodyEditText);
                //if (mUserToDoItem.getToDoDate() != null) {
//                    date = mUserToDoItem.getToDoDate();
                    //date = endDate;
                //} else {
                date = new Date();
                //}
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                theme = getActivity().getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(RecurringFragment.this, year, month, day);
                if (theme.equals(MainFragment.DARKTHEME)) {
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getActivity().getFragmentManager(), "DateFragment");

            }
        });

        doneRecurring = (Button) view.findViewById(R.id.doneBtn);
        doneRecurring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddToDoActivity.class);
                startActivity(in);
            }
        });

        recurringDays = (TextView) view.findViewById(R.id.selectDays);
        selectDay = new boolean[daysArray.length];

        recurringDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMultiChoiceItems(daysArray, selectDay, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            daysList.add(i);
                            Collections.sort(daysList);
                        }else {
                            daysList.remove(i);
                        }
                    }
                });

                //OK button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int k =0; k <daysList.size(); k++){
                            stringBuilder.append(daysArray[daysList.get(k)]);

                            if (k != daysList.size()-1){
                                stringBuilder.append(", ");
                            }
                        }

                        recurringDays.setText(stringBuilder.toString());
                    }
                });

                // cancel Button

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                // clear button

                builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for (int  k=0; k < selectDay.length; k++){
                            selectDay[k] = false;
                            daysList.clear();
                            recurringDays.setText(" ");
                        }
                    }
                });
                builder.show();
            }
        });

        return view;
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setDate(year, month, day);
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int hour, minute;
//        int currentYear = calendar.get(Calendar.YEAR);
//        int currentMonth = calendar.get(Calendar.MONTH);
//        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day);

        if (reminderCalendar.before(calendar)) {
            //    Toast.makeText(this, "My time-machine is a bit rusty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mUserReminderDate != null) {
            calendar.setTime(mUserReminderDate);
        }

        if (DateFormat.is24HourFormat(getContext())) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        } else {

            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);

        calendar.set(year, month, day, hour, minute);
        mUserReminderDate = calendar.getTime();
        //setReminderTextView();
//        setDateAndTimeEditText();
        setDateEditText();
    }
    public void setDateEditText() {
        String dateFormat = "d MMM, yyyy";
        mDateEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }
    public static String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }
}
