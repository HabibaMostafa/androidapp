package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.util.ArrayList; 


public class ToDoItem implements Serializable {
    private String mToDoText;
    private String mToDoStatus;
    private boolean mHasReminder;
    private ArrayList<String> labelList;
    //add description
    private String mToDoDescription;
    private String mToDoLabel;
    //    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    private Date dateAssigned;
    private UUID mTodoIdentifier;
    private String TAG = "ToDoItem";

    //used by the recurring function
    private Date startDate;
    private Date endDate;
    private String interval;
    private Boolean isRecurring;
    private Boolean isEndless;
    private Boolean hasStartDate;
    private Boolean hasLimit;
    private int timesRecurred;
    private int recurrenceLimit;

    private static final String RECURRENCE_START = "recurrence_start";
    private static final String RECURRENCE_END = "recurrence_end";
    private static final String RECURRENCE_INTERVAL = "recurrence_interval";
    private static final String RECURRENCE_ON = "recurrence_on";
    private static final String RECURRENCE_ENDLESS = "recurrence_endless";
    private static final String RECURRENCE_HAS_START = "recurrence_has_start";
    private static final String RECURRENCE_HAS_LIMIT = "recurrence_has_limit";
    private static final String RECURRENCE_AMT = "recurrence_amount";
    private static final String RECURRENCE_LIMIT = "recurrence_limit";


    //add description
    private static final String TODODESCRIPTION = "tododescription";
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
    //    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";
    private static final String TODOLABEL = "todolabel"; // unused now, just for testing
    private static final String TODOSTATUS = "todostatus";
    private static final String TODOLABELS = "todolabels";
    private static final String TODODATEASSIGNED = "dateassigned";


    // This constructor is called when the check mark is pressed for adding a new item
    public ToDoItem(String todoBody,String tododescription, boolean hasReminder, Date toDoDate) {
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mToDoStatus = "";
        mToDoLabel = "";
        mToDoDescription = tododescription;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
        labelList = new ArrayList<String>();
        dateAssigned = new Date();

        //init the recurrence related variables
        dateAssigned = new Date();
        startDate = new Date();
        endDate = new Date();
        interval = "";
        isRecurring = true;
        isEndless = false;
        hasStartDate = false;
        hasLimit = false;
        timesRecurred = 0;
        recurrenceLimit = 0;
    }


    // this constructor reads the information in the JSON object and updates the ToDoItem object
    public ToDoItem(JSONObject jsonObject) throws JSONException {
        mToDoText = jsonObject.getString(TODOTEXT);
        mToDoLabel = jsonObject.getString(TODOLABEL);
        mToDoDescription = jsonObject.getString(TODODESCRIPTION);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));
        mToDoStatus = jsonObject.getString(TODOSTATUS);

//        String test = (jsonObject.get(TODOLABELS)).toString();
        labelList = new ArrayList<String>();
        JSONArray jsonArray = (JSONArray)(jsonObject.get(TODOLABELS));
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject aLabel = (JSONObject)jsonArray.get(i);
            labelList.add(aLabel.getString("name"));
        }

//        if(jsonObject.has(TODOLASTEDITED)){
//            mLastEdited = new Date(jsonObject.getLong(TODOLASTEDITED));
//        }
        if (jsonObject.has(TODODATE)) {
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }



        // read the assigned date string from the JSON and convert it to Date
        String dateStr = "";
        dateStr = jsonObject.getString(TODODATEASSIGNED);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if(dateStr != null) {
            try {
                this.dateAssigned = formatter.parse(dateStr);
            } catch (Exception e) {
                Log.d("ToDoItem.java", e.toString());
            }
        } 


        // read the recurrence data


        try {
            this.isRecurring = Boolean.parseBoolean(jsonObject.getString(RECURRENCE_ON));
        } catch (Exception e){
            this.isRecurring = false;
        }

        if(this.isRecurring) {
            this.startDate = stringToDate(jsonObject.getString(RECURRENCE_START));
            this.endDate = stringToDate(jsonObject.getString(RECURRENCE_END));
            this.interval = jsonObject.getString(RECURRENCE_INTERVAL);
            this.isEndless = Boolean.parseBoolean(jsonObject.getString(RECURRENCE_ENDLESS));
            this.hasStartDate = Boolean.parseBoolean(jsonObject.getString(RECURRENCE_HAS_START));
            this.hasLimit = Boolean.parseBoolean(jsonObject.getString(RECURRENCE_HAS_LIMIT));
            this.timesRecurred = Integer.parseInt(jsonObject.getString(RECURRENCE_AMT));
            this.recurrenceLimit = Integer.parseInt(jsonObject.getString(RECURRENCE_LIMIT));

            Log.d(TAG, "start date: " + dateToString(startDate));
            Log.d(TAG, "end date: " + dateToString(endDate));
            Log.d(TAG, "interval: " + (interval));
            Log.d(TAG, "is recurring: " + String.valueOf(isRecurring));
            Log.d(TAG, "is endless: " + String.valueOf(isEndless));
            Log.d(TAG, "has a start date: " +  String.valueOf(hasStartDate));
            Log.d(TAG, "has a limit: " + String.valueOf(hasLimit));
            Log.d(TAG, "number recurred: " + Integer.toString(timesRecurred));
            Log.d(TAG, "recurrence limit: "+ Integer.toString(recurrenceLimit));
        }


        return;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODOREMINDER, mHasReminder);
        jsonObject.put(TODOLABEL, mToDoLabel);
        jsonObject.put(TODOSTATUS, mToDoStatus);
        jsonObject.put(TODODESCRIPTION, mToDoDescription);
        // jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if (mToDoDate != null) {
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        // put label array into the JSON obj
        JSONArray arr = new JSONArray();
        for(String str: labelList) {
            JSONObject labelsObj = new JSONObject();
            labelsObj.put("name",str);
            arr.put(labelsObj);
        }

        jsonObject.put(TODOLABELS, arr);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        jsonObject.put(TODODATEASSIGNED, df.format(dateAssigned));

        // add the recurrence information to the JSON file
        jsonObject.put(RECURRENCE_START, dateToString(startDate));
        jsonObject.put(RECURRENCE_END, dateToString(endDate));
        jsonObject.put(RECURRENCE_INTERVAL, interval);
        jsonObject.put(RECURRENCE_ON, String.valueOf(isRecurring));
        jsonObject.put(RECURRENCE_ENDLESS, String.valueOf(isEndless));
        jsonObject.put(RECURRENCE_HAS_START, String.valueOf(hasStartDate));
        jsonObject.put(RECURRENCE_HAS_LIMIT, String.valueOf(hasLimit));
        jsonObject.put(RECURRENCE_AMT, Integer.toString(timesRecurred));
        jsonObject.put(RECURRENCE_LIMIT, Integer.toString(recurrenceLimit));



        return jsonObject;
    }


    public ToDoItem() {
        this("Clean my room","Sweep and Mop my Room", true, new Date()); //what is this?
        labelList = new ArrayList<String>();
    }


    public String getmToDoStatus() {
        return mToDoStatus;
    }
    public void setmToDoStatus(String newStatus) {
        this.mToDoStatus = newStatus;
    }

    public String getmToDoLabel() {
        return mToDoLabel;
    }
    public void setmToDoLabel(String newLabel){this.mToDoLabel = newLabel;}

    public String getmToDoDescription() { return mToDoDescription;}

    public void setmToDoDescription(String mToDoDescription){this.mToDoDescription = mToDoDescription;}

    public String getToDoText() {
        if(mToDoText == null) {
            return "null";
        }

        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }

    public int getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }


    public UUID getIdentifier() {
        return mTodoIdentifier;
    }


    public void addLabel(String label) {

        if(label == null || this.labelList == null) {
            return;
        }

        if(labelList.contains(label) == false) {
            labelList.add(label);
        }
        return;
    }

    public void removeLabel(String label) {
        if(label == null || labelList == null) {
            return;
        }


        if(labelList.contains(label)) {
            labelList.remove(label);
        }
        return;
    }

    public ArrayList<String> getLabelList() {
        return this.labelList;
    }


    public Date getAssignedDate() {
        return dateAssigned;
    }

    public void setAssignedDate(Date date) {
        this.dateAssigned = date;
    }

    // returns the date attribute as a string with the following format MM/dd/yyyy HH:mm:ss
    public String assignedDateToString() {
        String converted = "";

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        converted = df.format(this.dateAssigned);
        return converted;
    }

    public String dateToString(Date theDate) {
        String converted = "";

        if(theDate == null) {
            return "null";
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        converted = df.format(theDate);
        return converted;
    }

    
    public Date stringToDate(String dateStr) {
        Date toConvert = new Date();
        toConvert = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if(dateStr != null) {
            try {
                toConvert = formatter.parse(dateStr);
            } catch (Exception e) {
                Log.d("ToDoItem.java", e.toString());
            }
        } 

        return toConvert;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public Boolean getEndless() {
        return isEndless;
    }

    public void setEndless(Boolean endless) {
        isEndless = endless;
    }

    public Boolean getHasStartDate() {
        return hasStartDate;
    }

    public void setHasStartDate(Boolean hasStartDate) {
        this.hasStartDate = hasStartDate;
    }

    public Boolean getHasLimit() {
        return hasLimit;
    }

    public void setHasLimit(Boolean hasLimit) {
        this.hasLimit = hasLimit;
    }

    public int getTimesRecurred() {
        return timesRecurred;
    }

    public void setTimesRecurred(int timesRecurred) {
        this.timesRecurred = timesRecurred;
    }

    public int getRecurrenceLimit() {
        return recurrenceLimit;
    }

    public void setRecurrenceLimit(int recurrenceLimit) {
        this.recurrenceLimit = recurrenceLimit;
    }
}

