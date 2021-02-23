package com.example.avjindersinghsekhon.minimaltodo.Reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Switch;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class ReminderActivity extends AppDefaultActivity {
    private SwitchCompat recurSwitch;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recurSwitch = (SwitchCompat)findViewById(R.id.switch_recurrent);
        recurSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recurActivity();
            }
        });
    }

    public void recurActivity(){
        Intent intent = new Intent(ReminderActivity.this, RecurringActivity.class);
        startActivity(intent);
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.reminder_layout;
    }

    @NonNull
    @Override
    protected ReminderFragment createInitialFragment() {
        return ReminderFragment.newInstance();
    }



}
