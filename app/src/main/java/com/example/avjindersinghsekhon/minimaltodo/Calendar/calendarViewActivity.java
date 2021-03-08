package com.example.avjindersinghsekhon.minimaltodo.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
    private Toolbar ctoolbar;
    String theme;

    //when activity is called and created.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);

        /*
        * Mode switch depending on the user selection (day or night mode)
        * */
        if (theme.equals(MainFragment.DARKTHEME)) {
            setTheme(R.style.CustomStyle_DarkTheme);
            setContentView(R.layout.activity_calendar_view);
        } else {
            setTheme(R.style.CustomStyle_LightTheme);
            setContentView(R.layout.activity_calendar_light_view);
        }

        /*
        * create the back arrow and get the actionBar
        * */
        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        ctoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ctoolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }

        //Button to return to main application page
//        btnReturn = (Button) findViewById(R.id.btnReturn);
//        btnReturn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //old way to change back to main activity
//                //Intent intent = new Intent(calendarViewActivity.this, MainFragment.class);
//                //startActivity(intent);
//
//                //new better way to change to main activity. finish() closes the opened activities
//                //this way activities and not over filling the stack.
//                finish();
//            }
//        });

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

    /*
    * Can be used to return to the home
    * */
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    /*
    * Navigating back to home
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}