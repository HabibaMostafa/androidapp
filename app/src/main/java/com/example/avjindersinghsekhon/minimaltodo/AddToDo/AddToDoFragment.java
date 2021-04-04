package com.example.avjindersinghsekhon.minimaltodo.AddToDo;

import android.animation.Animator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Reminder.RecurringActivity;
import com.example.avjindersinghsekhon.minimaltodo.Reminder.RecurringFragment;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;



import java.text.SimpleDateFormat;
// import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;




public class AddToDoFragment extends AppDefaultFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AddToDoFragment";
    private Date mLastEdited;

    private EditText mToDoTextBodyEditText;
    private EditText mToDoTextBodyLabel;
    private EditText mToDoTextBodyDescription;

    private RadioGroup mToDoStatusGroup;
    private RadioButton mToDoStatusRadioButton;

    private SwitchCompat mToDoDateSwitch;
    //    private TextView mLastSeenTextView;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private TextView mReminderTextView;

    private String CombinationText;

    private EditText mDateEditText;
    private EditText mTimeEditText;
    private String mDefaultTimeOptions12H[];
    private String mDefaultTimeOptions24H[];

    private Button mChooseDateButton;
    private Button mChooseTimeButton;
    private Button mCopyClipboard;

    //this is for add recurrance
//    private Button recurBtn;
    private boolean updateRecurringCalandar;
    private boolean updateRecurringCalandarEnd;
    private boolean updateRecurringCalandarTime;

    private ToDoItem mUserToDoItem;
    private FloatingActionButton mToDoSendFloatingActionButton;
    public static final String DATE_FORMAT = "MMM d, yyyy";
    public static final String DATE_FORMAT_MONTH_DAY = "MMM d";
    public static final String DATE_FORMAT_TIME = "H:m";

    private String mUserEnteredText;
    private String mUserEnteredLabel;
    private String mUserEnteredDescription;
    private String mUserChosenStatus;


    private boolean mUserHasReminder;
    private Toolbar mToolbar;
    private Date mUserReminderDate;
    private Date theAssignedDate;
    private int mUserColor;
    private boolean setDateButtonClickedOnce = false;
    private boolean setTimeButtonClickedOnce = false;
    private LinearLayout mContainerLayout;
    private String theme;
    AnalyticsApplication app;

    private ArrayList<String> labelList;
    public static final String LABELFILE = "labels.txt";
    private StoreRetrieveData labelData;

    private static View theView;
    private static ToDoItem theToDoItem;
    private static Context theContext;

    private boolean updateStartTime = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        updateRecurringCalandar = false;
        updateRecurringCalandarEnd = false;
        updateRecurringCalandarTime = false;
        updateStartTime = false;
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();
//        setContentView(R.layout.new_to_do_layout);
        //Need references to these to change them during light/dark mode
        ImageButton reminderIconImageButton;
        TextView reminderRemindMeTextView;


//        check the radio
        theme = getActivity().getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
        if (theme.equals(MainFragment.LIGHTTHEME)) {
            getActivity().setTheme(R.style.CustomStyle_LightTheme);
            Log.d("OskarSchindler", "Light Theme");
        } else {
            getActivity().setTheme(R.style.CustomStyle_DarkTheme);
        }


        //Show an X in place of <-
        final Drawable cross = getResources().getDrawable(R.drawable.ic_clear_white_24dp);
        if (cross != null) {
            cross.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
        }

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(cross);

        }


        mUserToDoItem = (ToDoItem) getActivity().getIntent().getSerializableExtra(MainFragment.TODOITEM);
        mUserEnteredText = mUserToDoItem.getToDoText();
        mUserEnteredLabel = mUserToDoItem.getmToDoLabel();
        mUserEnteredDescription = mUserToDoItem.getmToDoDescription();
        mUserHasReminder = mUserToDoItem.hasReminder();
        mUserReminderDate = mUserToDoItem.getToDoDate();
        mUserColor = mUserToDoItem.getTodoColor();
        mUserChosenStatus = mUserToDoItem.getmToDoStatus();
        theAssignedDate = mUserToDoItem.getAssignedDate();

        // check if there already is a date set, if its NULL set it as the current date
        // if(dateCreated != null) {
        //     Date date = new Date();
        //     this.dateCreated = date;
        // }


        // refer to the existing ToDoItem and check the appropriate radio button:
        checkToDoSavedStatus(view, mUserToDoItem);

        // listens to the radio if it is changed
        addListenerRadio(view);

//        if(mUserToDoItem.getLastEdited()==null) {
//            mLastEdited = new Date();
//        }
//        else{
//            mLastEdited = mUserToDoItem.getLastEdited();
//        }



        //display the current recurrence settings for this Item
        updateRecurrenceUI(view);




        reminderIconImageButton = (ImageButton) view.findViewById(R.id.userToDoReminderIconImageButton);
        reminderRemindMeTextView = (TextView) view.findViewById(R.id.userToDoRemindMeTextView);
        if (theme.equals(MainFragment.DARKTHEME)) {
            reminderIconImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_24dp));
            reminderRemindMeTextView.setTextColor(Color.WHITE);
        }



        //Button for Copy to Clipboard
        mCopyClipboard = (Button) view.findViewById(R.id.copyclipboard);

        mContainerLayout = (LinearLayout) view.findViewById(R.id.todoReminderAndDateContainerLayout);
        mUserDateSpinnerContainingLinearLayout = (LinearLayout) view.findViewById(R.id.toDoEnterDateLinearLayout);
        mToDoTextBodyEditText = (EditText) view.findViewById(R.id.userToDoEditText);
        mToDoTextBodyLabel = (EditText) view.findViewById(R.id.userToDoLabel);
        mToDoTextBodyDescription= (EditText) view.findViewById(R.id.userToDoDescription);
        mToDoDateSwitch = (SwitchCompat) view.findViewById(R.id.toDoHasDateSwitchCompat);
//        mLastSeenTextView = (TextView)findViewById(R.id.toDoLastEditedTextView);
        mToDoSendFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.makeToDoFloatingActionButton);
        mReminderTextView = (TextView) view.findViewById(R.id.newToDoDateTimeReminderTextView);


        //OnClickListener for CopyClipboard Button
        mCopyClipboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String toDoTextContainer = mToDoTextBodyEditText.getText().toString();
                String toDoLabelContainer = mToDoTextBodyLabel.getText().toString();
                String toDoTextBodyDescriptionContainer = mToDoTextBodyDescription.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                CombinationText = "Title : " + toDoTextContainer + "\nStatus: "+ mUserChosenStatus + "\nLabel: " + toDoLabelContainer +  "\nDescription : " + toDoTextBodyDescriptionContainer + "\n -Copied From MinimalToDo";
                ClipData clip = ClipData.newPlainText("text", CombinationText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied To Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });






        mContainerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(mToDoTextBodyEditText);
                hideKeyboard(mToDoTextBodyDescription);
            }
        });


        if (mUserHasReminder && (mUserReminderDate != null)) {
//            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
            setReminderTextView();
            setEnterDateLayoutVisibleWithAnimations(true);
        }
        if (mUserReminderDate == null) {
            mToDoDateSwitch.setChecked(false);
            mReminderTextView.setVisibility(View.GONE);
        }

//        TextInputLayout til = (TextInputLayout)findViewById(R.id.toDoCustomTextInput);
//        til.requestFocus();
        mToDoTextBodyEditText.requestFocus();

        mToDoTextBodyEditText.setText(mUserEnteredText);
        mToDoTextBodyLabel.setText(mUserEnteredLabel);
        mToDoTextBodyDescription.setText(mUserEnteredDescription);

        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mToDoTextBodyEditText, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mToDoTextBodyEditText.setSelection(mToDoTextBodyEditText.length());
        mToDoTextBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mToDoTextBodyLabel.setText(mUserEnteredLabel);
        mToDoTextBodyLabel.setSelection(mUserEnteredLabel.length());
        mToDoTextBodyLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredLabel = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mToDoTextBodyDescription.setText(mUserEnteredDescription);
        mToDoTextBodyDescription.setSelection(mToDoTextBodyDescription.length());
        mToDoTextBodyDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredDescription = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });


//        String lastSeen = formatDate(DATE_FORMAT, mLastEdited);
//        mLastSeenTextView.setText(String.format(getResources().getString(R.string.last_edited), lastSeen));

        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setChecked(mUserHasReminder && (mUserReminderDate != null));
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    app.send(this, "Action", "Reminder Set");
                } else {
                    app.send(this, "Action", "Reminder Removed");

                }

                if (!isChecked) {
                    mUserReminderDate = null;
                }
                mUserHasReminder = isChecked;
                setDateAndTimeEditText();
                setEnterDateLayoutVisibleWithAnimations(isChecked);
                hideKeyboard(mToDoTextBodyEditText);
                hideKeyboard(mToDoTextBodyDescription);
            }
        });


        mToDoSendFloatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                update the limit.
                ToDoItem item = mUserToDoItem;
                Log.d("addtodo","has a limit?" + String.valueOf(item.getHasLimit()) );
                if(item.getHasLimit()) {
                    EditText user_input = (EditText) theView.findViewById(R.id.recurrence_limit);
                    String inputted = user_input.getText().toString();
                    Log.d("addtodo", "limit " + inputted);


                    //error check if input is numeric
                    int charsNotNumeric = 0;
                    for(int i = 0; i < user_input.length(); i++ ) {
                        Character c = inputted.charAt(i);
                        if (!Character.isDigit(c)) {
                            charsNotNumeric++;
                        }
                    }

                    //Log.d("char", "chars not numeric: " + Integer.toString(charsNotNumeric));
                    //Log.d("char", "length " + Integer.toString(user_input.length()));

                    if(inputted.equals("")) {
                        item.setRecurrenceLimit(0);
                    }

                    else if(charsNotNumeric == 0) {
                        item.setRecurrenceLimit(Integer.parseInt(inputted));
                    }

                    else {
                        item.setRecurrenceLimit(0);
                    }
                }

                // get the current set date and time in the view and update the ToDoItem
                setStartDateAndTime(theView, item);

                //populate the dates
                item.populateRecurringDates();

                if (mToDoTextBodyEditText.length() <= 0) {
                    mToDoTextBodyEditText.setError(getString(R.string.todo_error));
                } else if (mUserReminderDate != null && mUserReminderDate.before(new Date())) {
                    app.send(this, "Action", "Date in the Past");
                    makeResult(RESULT_CANCELED);
                } else {
                    app.send(this, "Action", "Make Todo");
                    makeResult(RESULT_OK);
                    
                    getActivity().finish();
                }
                hideKeyboard(mToDoTextBodyEditText);
                hideKeyboard(mToDoTextBodyDescription);
            }
        });


        mDateEditText = (EditText) view.findViewById(R.id.newTodoDateEditText);
        mTimeEditText = (EditText) view.findViewById(R.id.newTodoTimeEditText);

        mDateEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if (mUserToDoItem.getToDoDate() != null) {
//                    date = mUserToDoItem.getToDoDate();
                    date = mUserReminderDate;
                } else {
                    date = new Date();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoFragment.this, year, month, day);
                if (theme.equals(MainFragment.DARKTHEME)) {
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getActivity().getFragmentManager(), "DateFragment");
            }
        });


        mTimeEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if (mUserToDoItem.getToDoDate() != null) {
//                    date = mUserToDoItem.getToDoDate();
                    date = mUserReminderDate;
                } else {
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoFragment.this, hour, minute, DateFormat.is24HourFormat(getContext()));
                if (theme.equals(MainFragment.DARKTHEME)) {
                    timePickerDialog.setThemeDark(true);
                }
                timePickerDialog.show(getActivity().getFragmentManager(), "TimeFragment");
            }
        });

//        mDefaultTimeOptions12H = new String[]{"9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM", "12:00 AM"};
//        mDefaultTimeOptions24H = new String[]{"9:00", "12:00", "15:00", "18:00", "21:00", "24:00"};
        setDateAndTimeEditText();

//

//        mChooseDateButton = (Button)findViewById(R.id.newToDoChooseDateButton);
//        mChooseTimeButton = (Button)findViewById(R.id.newToDoChooseTimeButton);
//
//        mChooseDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Date date;
//                hideKeyboard(mToDoTextBodyEditText);
//                if(mUserToDoItem.getToDoDate()!=null){
//                    date = mUserToDoItem.getToDoDate();
//                }
//                else{
//                    date = new Date();
//                }
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);
//                if(theme.equals(MainActivity.DARKTHEME)){
//                    datePickerDialog.setThemeDark(true);
//                }
//                datePickerDialog.show(getFragmentManager(), "DateFragment");
//            }
//        });
//
//        mChooseTimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Date date;
//                hideKeyboard(mToDoTextBodyEditText);
//                if(mUserToDoItem.getToDoDate()!=null){
//                    date = mUserToDoItem.getToDoDate();
//                }
//                else{
//                    date = new Date();
//                }
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoActivity.this, hour, minute, DateFormat.is24HourFormat(AddToDoActivity.this));
//                if(theme.equals(MainActivity.DARKTHEME)){
//                    timePickerDialog.setThemeDark(true);
//                }
//                timePickerDialog.show(getFragmentManager(), "TimeFragment");
//            }
//        });
        updateLabelList();
//        List<String> spinnerArr = new ArrayList<String>()

        // populate the spinnerlist
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_spinner_item, labelList) {
            //moves all labels to the center of the dropdown menu
            //https://stackoverflow.com/questions/7511049/set-view-text-align-at-center-in-spinner-in-android
            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(16);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.CENTER);

                return v;

            }
        };
        adapter.insert("Select label",0);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner)view.findViewById(R.id.labelSpinner);
        spinner.setAdapter(adapter);

        theView = view;
        theToDoItem = mUserToDoItem;
        theContext = getContext();

        spinner.setSelection(0, true);

        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0) {
                    return;
                }

                mUserToDoItem.addLabel(labelList.get(i));

                addLabelButtons(theView, theToDoItem, theContext);

                Log.d("label", mUserToDoItem.getLabelList().toString());
                spinner.setSelection(0, true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }



        });

        addLabelButtons(view, mUserToDoItem, getContext());




//        recurBtn = (Button) view.findViewById(R.id.recrBtn);
//
//        recurBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getActivity(), RecurringActivity.class);
//                startActivity(intent);
//            }
//        });





        // // check if there already is a date set, if its NULL set it as the current date
        // if(dateCreated != null) {
        //     Date date = new Date();
        //     this.dateCreated = date;
        // }

        // else {
        //     Log.d("debugTheDate", "dateCreated is null!");
        //     Date date = new Date();
        //     Log.d("debugTheDate", "a date is " + date.toString());
        //     // Log.d("debugTheDate", DateFormat.format(dateCreated));
        // }


        // get the current date and set dateCreated to the current date.

        return;


    }




    private void setDateAndTimeEditText() {

        if (mUserToDoItem.hasReminder() && mUserReminderDate != null) {
            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
            String formatToUse;
            if (DateFormat.is24HourFormat(getContext())) {
                formatToUse = "k:mm";
            } else {
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, mUserReminderDate);
            mTimeEditText.setText(userTime);
            mDateEditText.setText(userDate);

        } else {
            mDateEditText.setText(getString(R.string.date_reminder_default));
//            mUserReminderDate = new Date();
            boolean time24 = DateFormat.is24HourFormat(getContext());
            Calendar cal = Calendar.getInstance();
            if (time24) {
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            } else {
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
            }
            cal.set(Calendar.MINUTE, 0);
            mUserReminderDate = cal.getTime();
            Log.d("OskarSchindler", "Imagined Date: " + mUserReminderDate);
            String timeString;
            if (time24) {
                timeString = formatDate("k:mm", mUserReminderDate);
            } else {
                timeString = formatDate("h:mm a", mUserReminderDate);
            }
            mTimeEditText.setText(timeString);
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            if(hour<9){
//                timeOption = time24?mDefaultTimeOptions24H[0]:mDefaultTimeOptions12H[0];
//            }
//            else if(hour < 12){
//                timeOption = time24?mDefaultTimeOptions24H[1]:mDefaultTimeOptions12H[1];
//            }
//            else if(hour < 15){
//                timeOption = time24?mDefaultTimeOptions24H[2]:mDefaultTimeOptions12H[2];
//            }
//            else if(hour < 18){
//                timeOption = time24?mDefaultTimeOptions24H[3]:mDefaultTimeOptions12H[3];
//            }
//            else if(hour < 21){
//                timeOption = time24?mDefaultTimeOptions24H[4]:mDefaultTimeOptions12H[4];
//            }
//            else{
//                timeOption = time24?mDefaultTimeOptions24H[5]:mDefaultTimeOptions12H[5];
//            }
//            mTimeEditText.setText(timeOption);
        }
    }

    private String getThemeSet() {
        return getActivity().getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
    }

    public void hideKeyboard(EditText et) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }


    private void updateLabelList() {
        labelData = new StoreRetrieveData(getContext());
        try {
            labelList = labelData.loadLabels();
        } catch(Exception e) {
            Log.d("label", "did not load" + e.toString());
            labelList = null;
        }
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
        setReminderTextView();
//        setDateAndTimeEditText();
        setDateEditText();
    }


    public void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        if (mUserReminderDate != null) {
            calendar.setTime(mUserReminderDate);
        }

//        if(DateFormat.is24HourFormat(this) && hour == 0){
//            //done for 24h time
//                hour = 24;
//        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("OskarSchindler", "Time set: " + hour);
        calendar.set(year, month, day, hour, minute, 0);
        mUserReminderDate = calendar.getTime();

        setReminderTextView();
//        setDateAndTimeEditText();
        setTimeEditText();
    }

    public void setDateEditText() {
        String dateFormat = "d MMM, yyyy";
        mDateEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void setTimeEditText() {
        String dateFormat;
        if (DateFormat.is24HourFormat(getContext())) {
            dateFormat = "k:mm";
        } else {
            dateFormat = "h:mm a";

        }
        mTimeEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void setReminderTextView() {
        if (mUserReminderDate != null) {
            mReminderTextView.setVisibility(View.VISIBLE);
            if (mUserReminderDate.before(new Date())) {
                Log.d("OskarSchindler", "DATE is " + mUserReminderDate);
                mReminderTextView.setText(getString(R.string.date_error_check_again));
                mReminderTextView.setTextColor(Color.RED);
                return;
            }
            Date date = mUserReminderDate;
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";

            if (DateFormat.is24HourFormat(getContext())) {
                timeString = formatDate("k:mm", date);
            } else {
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            mReminderTextView.setTextColor(getResources().getColor(R.color.secondary_text));
            mReminderTextView.setText(finalString);
        } else {
            mReminderTextView.setVisibility(View.INVISIBLE);

        }
    }

    public void makeResult(int result) {
        Log.d(TAG, "makeResult - ok : in");
        Intent i = new Intent();
        if (mUserEnteredText.length() > 0) {

            String capitalizedString = Character.toUpperCase(mUserEnteredText.charAt(0)) + mUserEnteredText.substring(1);
            mUserToDoItem.setToDoText(capitalizedString);


            Log.d(TAG, "Label: " + mUserEnteredLabel);
            mUserToDoItem.setmToDoLabel(mUserEnteredLabel);

            Log.d(TAG, "Status: " + mUserChosenStatus);
            mUserToDoItem.setmToDoStatus(mUserChosenStatus);

            Log.d(TAG, "Description: " + mUserEnteredDescription);
            mUserToDoItem.setmToDoDescription(mUserEnteredDescription);
        } else {
            mUserToDoItem.setToDoText(mUserEnteredText);

            Log.d(TAG, "Status: " + mUserChosenStatus);
            mUserToDoItem.setmToDoStatus(mUserChosenStatus);

            Log.d(TAG, "Label: " + mUserEnteredLabel);
            mUserToDoItem.setmToDoLabel(mUserEnteredLabel);

            Log.d(TAG, "Description: " + mUserEnteredDescription);
            mUserToDoItem.setmToDoDescription(mUserEnteredDescription);
        }
//        mUserToDoItem.setLastEdited(mLastEdited);
        if (mUserReminderDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mUserReminderDate);
            calendar.set(Calendar.SECOND, 0);
            mUserReminderDate = calendar.getTime();
        }
        mUserToDoItem.setHasReminder(mUserHasReminder);
        mUserToDoItem.setToDoDate(mUserReminderDate);
        mUserToDoItem.setTodoColor(mUserColor);
        i.putExtra(MainFragment.TODOITEM, mUserToDoItem);
        getActivity().setResult(result, i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    app.send(this, "Action", "Discard Todo");
                    makeResult(RESULT_CANCELED);
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                hideKeyboard(mToDoTextBodyEditText);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {

        if(updateStartTime) {
            Log.d("starttime", "update the clock!!!!");
            setStartTimeUI(theView, hour, minute);
        }
        setTime(hour, minute);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        if(updateRecurringCalandar) {
            setStartDate(year, month, day);
            updateRecurringCalandar = false;
        }

        if(updateRecurringCalandarEnd) {
            setEndDate(year, month, day);
            updateRecurringCalandarEnd = false;
        }
        else {
            setDate(year, month, day);
        }
    }

    public void setEnterDateLayoutVisible(boolean checked) {
        if (checked) {
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.GONE);
        }
    }

    public void setEnterDateLayoutVisibleWithAnimations(boolean checked) {
        if (checked) {
            setReminderTextView();
            mUserDateSpinnerContainingLinearLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        } else {
            mUserDateSpinnerContainingLinearLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }

    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_add_to_do;
    }

    public static AddToDoFragment newInstance() {
        return new AddToDoFragment();
    }

    private void addLabelButtons(View view, final ToDoItem todo, Context context) {

        if(view == null || todo == null || context == null) {
            return;
        }

        // Get list of labels
        ArrayList<String> labels = todo.getLabelList();

        // Add colour to labels to enhance categorization
        ArrayList<String> colours = new ArrayList<>();

        // Initialize colours to be used for labels
        colours.add("#f58a38");
        colours.add("#1590ed");
        colours.add("#8f30bf");
        colours.add("#199c40");
        colours.add("#b02000");
        colours.add("#38a4ba");

        int colourIndex = 0;

        // labelButtonContainer
        final LinearLayout container = (LinearLayout) view.findViewById(R.id.labelButtonContainer);

        container.removeAllViews();// need this or else if a button is selected twice it will be added twice

        // array list of labels assigned to the ToDoItem
        for(final String str: labels) {
            Button btn = new Button(context);
            String chosenColour = colours.get(colourIndex);
            btn.setBackgroundColor(Color.parseColor(chosenColour)); // From android.graphics.Color
            btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            btn.setText(str);
            btn.setTag(str + "_label_btn");
            container.addView(btn);


            colourIndex++;

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // remove the label from the toDo item

                    todo.removeLabel(str);

                    // delete this button
                    container.removeView(v);
                    
                    todo.removeLabel(str); // remove the label from the toDo item
                    container.removeView(v); // delete button
                }
            });
        }
        
        return;
    }


    public void addLabelButton(View view, final ToDoItem todo, final String str, Context context) {

        if(view == null || todo == null || context == null || str== null) {
            return;
        }

        final LinearLayout container = (LinearLayout) view.findViewById(R.id.labelButtonContainer);
        Button btn = new Button(context);
        btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btn.setText(str);
        btn.setTag(str + "_label_btn");
        container.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // remove the label from the toDo item

                todo.removeLabel(str);

                // delete this button
                container.removeView(v);
                
            }
        });
        return;
    }

    private void addListenerRadio(View view) {

        mToDoStatusGroup = (RadioGroup) view.findViewById(R.id.toDoStatusGroup);
        int selected = mToDoStatusGroup.getCheckedRadioButtonId();
        mToDoStatusRadioButton = (RadioButton) view.findViewById(selected);
//        mUserChosenStatus = (String) mToDoStatusRadioButton.getText();

        mToDoStatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioBtn = (RadioButton)radioGroup.findViewById(i);
                boolean isSelected = checkedRadioBtn.isChecked();

                if(isSelected) {
                    mUserChosenStatus = (String)checkedRadioBtn.getText();

                }
            }
        });
        return;
    }
    private void checkToDoSavedStatus(View view, ToDoItem mUserToDoItem) {
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.toDoStatusGroup);
        rg.clearCheck();

        //default will be set to incomplete
        rg.check(R.id.toDoRadioIncomplete);

        String status = mUserToDoItem.getmToDoStatus();

        if(status.equals("Complete")) {
            rg.check(R.id.toDoRadioComplete);
        }

        else if (status.equals("In Progress")) {
            rg.check(R.id.toDoRadioInProgress);
        }

//        else if (status.equals("Incomplete")) {
//            rg.check(R.id.toDoRadioIncomplete);
//        }


        return;
    }

    Date getCurrentDate() {

        return null;
    }

    String dateToString(Date date) {
        return "";
    }

    private void setupIntervalSpinner(View the_view) {

        ArrayList<String> intervals = new ArrayList<String>();
        intervals.add("Day");
        intervals.add("Week");
        intervals.add("Two Weeks");
        intervals.add("Month");
        intervals.add("Year");

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_spinner_item, intervals) {
            //moves all labels to the center of the dropdown menu
            //https://stackoverflow.com/questions/7511049/set-view-text-align-at-center-in-spinner-in-android
            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(16);
                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;

            }
        };


        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        final Spinner spinner = (Spinner)the_view.findViewById(R.id.repeat_spinner);
        spinner.setAdapter(adapter);

        ToDoItem item = mUserToDoItem;
        theContext = getContext();
        int selectedIndex = 0;

        //check the ToDoItem and selected the saved interval (if there is one, else set default to day)
        String interval = item.getInterval();
        if(interval == null) {
            item.setInterval("Day");
        }
        else {
            if (interval.equals("Day")) {
                selectedIndex = 0;
            } else if (interval.equals("Week")) {
                selectedIndex = 1;
            } else if (interval.equals("Two Weeks")) {
                selectedIndex = 2;
            } else if (interval.equals("Month")) {
                selectedIndex = 3;
            } else if (interval.equals("Year")) {
                selectedIndex = 4;
            }
        }

        spinner.setSelection(selectedIndex, true);
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i) {
                    case 0:
                        mUserToDoItem.setInterval("Day");
                        break;

                    case 1:
                        mUserToDoItem.setInterval("Week");
                        break;

                    case 2:
                        mUserToDoItem.setInterval("Two Weeks");
                        break;

                    case 3:
                        mUserToDoItem.setInterval("Month");
                        break;

                    case 4:
                        mUserToDoItem.setInterval("Year");

                        break;

                    default:
                        break;
                }

                spinner.setSelection(i, true);
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return;
    }

    //listens if the recurrence button is on or off and updates the item state
    private void setRecurringOnListener(View v) {
        SwitchCompat recurringSwitch = (SwitchCompat) v.findViewById(R.id.recurring_switch);
        final LinearLayout recurringLayout = (LinearLayout) v.findViewById(R.id.recurring_date_picker);

        recurringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mUserToDoItem.setRecurring(true);
                    // show the area
                    recurringLayout.setVisibility(View.VISIBLE);

                } else {
                    mUserToDoItem.setRecurring(false);
                    // maybe hide the area?
                    recurringLayout.setVisibility(View.GONE);
                }
            }
        });
        return;
    }

    private void updateStartDate(View v) {
        ToDoItem item = mUserToDoItem;
        EditText recurrenceStart = (EditText) v.findViewById(R.id.recurring_start_date);
        Date savedDate = item.getStartDate();

        //If no date was saved, set the date as today
        if(savedDate == null) {
            Date newDate = new Date();
            item.setStartDate(newDate);
        }

        //update the UI to show the date
        String dateToString = item.dateToStringNoTime(item.getStartDate());
        recurrenceStart.setText(dateToString);

        return;
    }

    // called when the start date calendar is modified.
    public void setStartDate(int year, int month, int day) {

        Calendar cal = new GregorianCalendar(year,month,day,00,00,00);
        ToDoItem item = mUserToDoItem;
        Date changedDate = cal.getTime();
        item.setStartDate(changedDate);
        updateStartDate(theView);
//        updateStartEditTime(theView);
        return;
    }

    public void updateStartEditTime(View view){

//        EditText startTimeEditText = (EditText) view.findViewById(R.id.recurring_start_time);
//
//        ToDoItem item = mUserToDoItem;
//        Date savedDate = item.getStartDate();
//
//        if(savedDate == null) {
//            Date newDate = new Date();
//            item.setStartDate(newDate);
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(savedDate);
//
//        int hour = calendar.HOUR_OF_DAY;
//        int minute = calendar.MINUTE;
//
//        String timeFormatString = hour + " : " + minute;
//
//        startTimeEditText.setText(timeFormatString);
//        return;
    }

    public void setEndDate(int year, int month, int day) {

        Calendar cal = new GregorianCalendar(year,month,day,00,00,00);
        ToDoItem item = mUserToDoItem;
        Date changedDate = cal.getTime();
        item.setEndDate(changedDate);
        updateEndDate(theView);
        return;
    }

    // setup the date picker functionality
    private void setStartDateListener(View v) {

        EditText recurrenceStart = (EditText) v.findViewById(R.id.recurring_start_date);


        /******************************************************/

//        EditText recurrenceStartTime = (EditText) v.findViewById(R.id.recurring_start_time);
        final ToDoItem item = mUserToDoItem;
        final Date savedDate = item.getStartDate();
//
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(savedDate);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);
        EditText startTime = (EditText) v.findViewById(R.id.recurring_start_time);
//        final int hour = 0;
//        final int minute = 0;
        /******************************************************/

        recurrenceStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItem item = mUserToDoItem;
                Date savedDate = item.getStartDate();

                //If no date was saved, set the date as today
                if(savedDate == null) {
                    Date newDate = new Date();
                    item.setStartDate(newDate);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(savedDate);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoFragment.this, year, month, day);
                if (theme.equals(MainFragment.DARKTHEME)) {
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getActivity().getFragmentManager(), "DateFragment");
                updateRecurringCalandar = true;
            }
        });
        
        /*******************************************/
        startTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(savedDate == null) {
//                    Date newDate = new Date();
//                    item.setStartDate(newDate);
//                }

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoFragment.this, hour, minute, DateFormat.is24HourFormat(getContext()));
                if (theme.equals(MainFragment.DARKTHEME)) {
                    timePickerDialog.setThemeDark(true);
                }
                timePickerDialog.show(getActivity().getFragmentManager(), "TimeFragment");
//                updateRecurringCalandarTime = true;
                updateStartTime = true;
            }
        });
        /*******************************************/ 
        return;
    }

//    checks what the existing end condition is set in the ToDo Item.
    private void updateEndCondition(View v) {
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.recurring_end_group);
        rg.clearCheck();
        rg.check(R.id.recurrence_radio_endless);
        ToDoItem item = mUserToDoItem;

        boolean isEndless = item.getEndless();
        boolean hasEndDate = item.getHasEndDate();
        boolean hasLimit = item.getHasLimit();

        // check if all are false
        boolean allFalse = false;
        if (isEndless==false && hasEndDate==false && hasLimit==false) {
            allFalse = true;
            Log.d("radio", "all false");
        }

        Log.d("radio", "checked if false");

        EditText limitInput = (EditText) v.findViewById(R.id.recurrence_limit);
        EditText endDateInput = (EditText) v.findViewById(R.id.recurring_end_date);
        limitInput.setVisibility(View.INVISIBLE);
        endDateInput.setVisibility(View.INVISIBLE);

        // if all are endless, just set the default (Endless)
        if(allFalse) {
            item.setEndless(true);
        }

        //check off the selected radio button according to what is saved in the item
        if(isEndless) {
            rg.check(R.id.recurrence_radio_endless);
            limitInput.setVisibility(View.INVISIBLE);
            endDateInput.setVisibility(View.INVISIBLE);
        }

        else if(hasEndDate) {
            rg.check(R.id.recurrence_radio_end_date);
            limitInput.setVisibility(View.INVISIBLE);
            endDateInput.setVisibility(View.VISIBLE);
        }

        else if(hasLimit) {
            rg.check(R.id.recurrence_radio_limit);
            limitInput.setVisibility(View.VISIBLE);
            endDateInput.setVisibility(View.INVISIBLE);
        }

        return;
    }

    public void setEndConditionListener(View v) {
        final RadioGroup rg = (RadioGroup) v.findViewById(R.id.recurring_end_group);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton sel = (RadioButton) v.findViewById(selected);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioBtn = (RadioButton)radioGroup.findViewById(i);
                boolean isSelected = checkedRadioBtn.isChecked();
                ToDoItem item = mUserToDoItem;
//                Log.d("radio","picked " + Integer.toString(i));

                int endlessId = R.id.recurrence_radio_endless;
                int endDateID = R.id.recurrence_radio_end_date;
                int LimitId = R.id.recurrence_radio_limit;


                EditText limitInput = (EditText) theView.findViewById(R.id.recurrence_limit);
                EditText endDateInput = (EditText) theView.findViewById(R.id.recurring_end_date);
                if(i == endlessId) {
                    rg.check(endlessId);
                    item.setEndless(true);
                    limitInput.setVisibility(View.INVISIBLE);
                    endDateInput.setVisibility(View.INVISIBLE);
                }

                else if(i == endDateID) {
                    rg.check(endDateID);
                    item.setHasEndDate(true);
                    limitInput.setVisibility(View.INVISIBLE);
                    endDateInput.setVisibility(View.VISIBLE);
                }

                else if(i == LimitId) {
                    rg.check(LimitId);
                    item.setHasLimit(true);
                    endDateInput.setVisibility(View.INVISIBLE);
                    limitInput.setVisibility(View.VISIBLE);
                }
            }
        });

        return;
    }

    //update the start date
    private void updateEndDate(View v) {
        ToDoItem item = mUserToDoItem;
        EditText recurrenceEnd = (EditText) v.findViewById(R.id.recurring_end_date);
        Date savedDate = item.getEndDate();

        //If no date was saved, set the date as today
        if(savedDate == null) {
            Date newDate = new Date();
            item.setEndDate(newDate);
        }
        //update the UI to show the date
        String dateToString = item.dateToStringNoTime(item.getEndDate());
        recurrenceEnd.setText(dateToString);

        return;
    }

    private void setEndDateListener(View v) {
        EditText recurrenceEnd = (EditText) v.findViewById(R.id.recurring_end_date);
        recurrenceEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItem item = mUserToDoItem;
                Date savedDate = item.getEndDate();

                //If no date was saved, set the date as today
                if(savedDate == null) {
                    Date newDate = new Date();
                    item.setStartDate(newDate);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(savedDate);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoFragment.this, year, month, day);
                if (theme.equals(MainFragment.DARKTHEME)) {
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getActivity().getFragmentManager(), "DateFragment");
                updateRecurringCalandarEnd = true;
            }
        });

        return;
    }

    private void updateLimitValue(View v) {
        ToDoItem item = mUserToDoItem;
        EditText user_input = (EditText) v.findViewById(R.id.recurrence_limit);
        int limit = item.getRecurrenceLimit();
        String limitStr = Integer.toString(limit);

        user_input.setText(limitStr);

        return;
    }

    private void setLimitOnChangeListener(View v) {
//        final ToDoItem item = mUserToDoItem;
//        EditText user_input = (EditText) v.findViewById(R.id.recurrence_limit);
//        user_input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d("limit", "text changed to " + s.toString() );
//                int conversion = Integer.parseInt(s.toString());
//
//                try {
//                    conversion = Integer.parseInt(s.toString());
//                } catch (Exception e) {
//                    Log.d("textchanged", e.toString());
//                    conversion = 0;
//                }
////                item.setRecurrenceLimit(conversion);
//            }
//        });


        return;
    }
    private void updateRecurrenceUI(View v) {
        ToDoItem item = mUserToDoItem;

        //update the recurrence on switch.
        final LinearLayout recurringLayout = (LinearLayout) v.findViewById(R.id.recurring_date_picker);
        SwitchCompat recurringSwitch = (SwitchCompat) v.findViewById(R.id.recurring_switch);
        recurringSwitch.setChecked(item.getRecurring());
        if(item.getRecurring()) {
            recurringLayout.setVisibility(View.VISIBLE);
        } else {
            recurringLayout.setVisibility(View.GONE);
        }

        //update the start date
        updateStartDate(v);

        //update the time
        setTimeToStartDateTime(v);

        //set a listener for the start date
        setStartDateListener(v);

        //listener for start time
        updateStartEditTime(v);

        //set a listener for the switch
        setRecurringOnListener(v);

        //populate the recurrence repeat interval spinner update and set listener
        setupIntervalSpinner(v);

        //update the selected radio button
        updateEndCondition(v);

        //set the end condition radio listener
        setEndConditionListener(v);

        //update the end date
        updateEndDate(v);

        //set the end date listener
        setEndDateListener(v);

        // update the limit value
        updateLimitValue(v);

        //create on change listener.
        setLimitOnChangeListener(v);


        return;
    }


    private void setStartDateAndTime(View v, ToDoItem item) {



        EditText startDate = (EditText) v.findViewById(R.id.recurring_start_date);
        EditText startTime = (EditText) v.findViewById(R.id.recurring_start_time);

        String dateStr = String.valueOf(startDate.getText());
        String timeStr = String.valueOf(startTime.getText());

        //remove all white space from the date time strings
        dateStr = dateStr.replaceAll("\\s+","");
        timeStr = timeStr.replaceAll("\\s+","");
        String combined = dateStr + " " + timeStr;

        Log.d("starttime", "Date no white space: " + dateStr);
        Log.d("starttime", "Time no white space: " + timeStr);
        Log.d("starttime", "Combined: " + combined);

        //formatter for the string to be converted to a date
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
//        combined = "01/01/2009 4:56";
        Date convertedDate = null;

        if(combined != null) {
            try {
                convertedDate = formatter.parse(combined);
            } catch (Exception e) {
                Log.d("AdToDoFragment.java", e.toString());
            }
        }

        if(convertedDate != null) {
            item.setStartDate(convertedDate);
        }

        return;
    }




    private void setStartTimeUI(View view, int hour, int min) {
        EditText startTime = (EditText) view.findViewById(R.id.recurring_start_time);

        String hourString = Integer.toString(hour);
        String minuteString = Integer.toString(min);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, min);


        String toSet = mUserToDoItem.dateToString(cal.getTime(), "hh:mm aa");
        startTime.setText(toSet);
        return;
    }



    void setTimeToStartDateTime(View view) {
        Date start = mUserToDoItem.getStartDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);

        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        EditText startTime = (EditText) view.findViewById(R.id.recurring_start_time);
        String hourString = Integer.toString(hour);
        String minuteString = Integer.toString(minute);

        String converted = mUserToDoItem.dateToString(cal.getTime(), "hh:mm aa");
        startTime.setText(converted);
        return;
    }



}
