package com.example.avjindersinghsekhon.minimaltodo.Reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

import java.util.ArrayList;
import java.util.Collections;

public class RecurringFragment extends Fragment {

    private Button doneRecurring;
    private Button cancelBtn;
    private TextView recurringDays;
    private boolean[] selectDay;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday", "Saturday"};


    public RecurringFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recurring, container, false);

        doneRecurring = (Button) view.findViewById(R.id.doneBtn);
        doneRecurring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddToDoActivity.class);
                in.putExtra("Days", recurringDays.getText().toString());
                startActivity(in);
//                finish();
            }
        });

        /*
        * cancel button 
        */
        doneRecurring = (Button) view.findViewById(R.id.doneBtn);
        doneRecurring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddToDoActivity.class);
                in.putExtra("Days", recurringDays.getText().toString());
                startActivity(in);
//                finish();
            }
        });

        cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(cancelIntent);
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


}
