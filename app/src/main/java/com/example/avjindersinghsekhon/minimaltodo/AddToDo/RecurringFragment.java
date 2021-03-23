package com.example.avjindersinghsekhon.minimaltodo.AddToDo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.avjindersinghsekhon.minimaltodo.R;

import java.util.Calendar;


public class RecurringFragment extends DialogFragment {

    private static final String TAG = "RecurringDialog" ;

    private Button doneBtn;
    private Button cancelBtn;
    private RadioGroup radioButtonEnds;
    private RadioButton radioButton;

    private EditText mstartDate, mstartTime, moccurrences;
    Spinner spinnerRepeat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recurring, container, false);

        doneBtn = (Button) view.findViewById(R.id.doneButton);
        cancelBtn = (Button) view.findViewById(R.id.cancelButton);
        mstartDate = (EditText) view.findViewById(R.id.startDate);
        mstartTime = (EditText)view.findViewById(R.id.startTime);
        spinnerRepeat = (Spinner)view.findViewById(R.id.spinnerFreq);
        radioButtonEnds = (RadioGroup) view.findViewById(R.id.repeatRadio);

        mstartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar startRecurDate = Calendar.getInstance();
                int mYear = startRecurDate.get(Calendar.YEAR);
                int mMonth = startRecurDate.get(Calendar.MONTH);
                int mDay = startRecurDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        mstartDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

//        radioButtonEnds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
////                if(checkedId==R.id.rdoNever)
////                {
//////                    datePicker.setEnabled(false);
//////                    afterN.setEnabled(false);
////                }
////
////                if(checkedId==R.id.rdoOn)
////                {
//////                    datePicker.setEnabled(true);
//////                    afterN.setEnabled(false);
////                }
////
////                if (checkedId==R.id.rdoAfter)
////                {
//////                    datePicker.setEnabled(false);
//////                    afterN.setEnabled(true);
////                }
//
//                String valueRadioButton =  ((RadioButton) getView().findViewById(radioGroup.getCheckedRadioButtonId()))
//                        .getText().toString();
//
//                if(valueRadioButton.equals("On")){
//
//                    // show the datepicker for end date
//
//
//                }else if(valueRadioButton.equals("After")){
//                    // show occurrences text box
//                }
//
//
//
//            }
//        });



        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Passing data");

                String spinnerText = spinnerRepeat.getSelectedItem().toString();
                String startDate = mstartDate.getText().toString();

//                int selectID = radioButtonEnds.getCheckedRadioButtonId();
//                radioButton = (RadioButton) view.findViewById(selectID);

                String valueRadioButton =
                        ((RadioButton) getView().findViewById(radioButtonEnds.getCheckedRadioButtonId()))
                                .getText().toString();


                String input = " " + startDate +" " + spinnerText + " Ends " + valueRadioButton ;
                if(!input.equals("")) {

//                    AddToDoFragment frg = (AddToDoFragment) getActivity().getSupportFragmentManager()
//                            .findFragmentByTag("AddToDoFragment");
//                    frg.mReceiveRecurData.setText(input);
                    AddToDoFragment.varDate = input;
                    AddToDoFragment.setDataRecieve();

                }


                getDialog().dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing dialog");
                getFragmentManager().popBackStack();
                getDialog().dismiss();
            }
        });


        return view;
    }

//    @Override
//    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//
//    }
}