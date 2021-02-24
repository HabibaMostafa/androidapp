package com.example.avjindersinghsekhon.minimaltodo.Reminder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.avjindersinghsekhon.minimaltodo.R;

public class RecurringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.rec1);
//
//        if(fragment == null){
//            fragment = new RecurringFragment();
//            fragmentManager.beginTransaction().add(R.id.rec1, fragment).commit();
//        }

    }
}