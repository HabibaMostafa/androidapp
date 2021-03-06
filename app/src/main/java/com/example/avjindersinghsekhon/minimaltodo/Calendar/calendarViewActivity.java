package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class calendarViewActivity extends AppCompatActivity {

    //activity to set up calendar view function.

    private static final String TAG = "calendarViewActivity";
    private CalendarView mCalendarView;
    private Button btnReturn;

    //when activity is called and created.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        //Button to return to main application page
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //old way to change back to main activity
                //Intent intent = new Intent(calendarViewActivity.this, MainFragment.class);
                //startActivity(intent);

                //new better way to change to main activity. finish() closes the opened activities
                //this way activities and not over filling the stack.
                finish();
            }
        });


        //listener in place for future implementation
        //Calendar day listener
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            Toast toast = null;
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {

//                String date = year + "/" + month + "/"+ dayOfMonth ;
//                Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
//                Toast toast = Toast.makeText(getContext(), "Hii", LENGTH_LONG).show();
//                Intent intent = new Intent(calendarViewActivity.this, MainFragment.class);
//                intent.putExtra("date",date);
//                startActivity(intent);

                Context context;
                context = getApplicationContext();
                int duration = LENGTH_LONG;

                //Convert the numerical month to it's full name
                SimpleDateFormat parsedMonth = new SimpleDateFormat("MM");
                SimpleDateFormat rawMonth = new SimpleDateFormat("MMMM");
                String strMonth ="";
                try {
                    strMonth = rawMonth.format(parsedMonth.parse(Integer.toString(month+1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //format the string to display to the user
                CharSequence text = String.format("Date Selected: %s %d, %d", strMonth, dayOfMonth, year);

                // cancel the previous toast if it exists
                if(toast != null) {
                    toast.cancel();
                }

                // create the toast and set the text and duration
                toast = Toast.makeText(context, text, duration);

                //displays the message at the bottom
                toast.show();


            }
        });
    }
}