package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class calendarViewActivity extends AppCompatActivity {

    //activity to set up calendar view function.

    private  static final String TAG = "calendarViewActivity";
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
        //mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        //mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //@Override
            //public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
            //    String date = year + "/" + month + "/"+ dayOfMonth ;
            //    Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
            //    Intent intent = new Intent(calendarViewActivity.this, MainFragment.class);
            //    intent.putExtra("date",date);
            //    startActivity(intent);
            //}
        //});
    }
}