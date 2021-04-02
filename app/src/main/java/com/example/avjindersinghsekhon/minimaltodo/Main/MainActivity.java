package com.example.avjindersinghsekhon.minimaltodo.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.avjindersinghsekhon.minimaltodo.About.AboutActivity;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Settings.SettingsActivity;
import com.example.avjindersinghsekhon.minimaltodo.Calendar.calendarViewActivity;
import com.example.avjindersinghsekhon.minimaltodo.Utility.CalendarDate;

public class MainActivity extends AppDefaultActivity {

    private ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);

            //if data is present in Calendar Date object, then display the selected date within main list view
            if (CalendarDate.selectedDate.contains("noData")){
                setActionBarText("Minimal");
            } else {
                String date = "temp";
                if (CalendarDate.getSelectedDate().contains("00:00:00")){
                    String remove = "00:00:00 EDT 2021";
                    date = CalendarDate.getSelectedDate().substring(0, CalendarDate.getSelectedDate().length() - remove.length());
                    setActionBarText("Minimal ToDo:  " + date);
                }
            }
        }
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuCalendarItem:
                Intent newIntent = new Intent(this, calendarViewActivity.class);
                startActivity(newIntent);
                return true;

//            case R.id.menuResetCalendarItem:
////                CalendarDate.setSelectedDate("noData");
////                CalendarDate.setDateChanged(true);
////                MainFragment newFragment = new MainFragment();
////                newFragment.resetCalendar();
//                return true;

            case R.id.aboutMeMenuItem:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
//            case R.id.switch_themes:
//                if(mTheme == R.style.CustomStyle_DarkTheme){
//                    addThemeToSharedPreferences(LIGHTTHEME);
//                }
//                else{
//                    addThemeToSharedPreferences(DARKTHEME);
//                }
//
////                if(mTheme == R.style.CustomStyle_DarkTheme){
////                    mTheme = R.style.CustomStyle_LightTheme;
////                }
////                else{
////                    mTheme = R.style.CustomStyle_DarkTheme;
////                }
//                this.recreate();
//                return true;
            case R.id.preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarText(String text){
        actionBar.setTitle(text);
    }
}


