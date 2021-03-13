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
    private static final String TODODATECREATED = "dateassigned";


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

        // dateCreated = null;
    }


    // this constructor is used
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



        //

        // read the assigned date string from the JSON and convert it to Date
        String dateStr = "";
        dateStr = jsonObject.getString(TODODATECREATED);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if(dateStr != null) {

            // Log.d("debugTheDate", dateStr);
            try {
                this.dateAssigned = formatter.parse(dateStr);
            } catch (Exception e) {
                Log.d("ToDoItem.java", e.toString());
            }
        } 
        // else {
        //     // dateAssigned = new Date();
        // }

    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODOREMINDER, mHasReminder);
        jsonObject.put(TODOLABEL, mToDoLabel);
        jsonObject.put(TODOSTATUS, mToDoStatus);
        jsonObject.put(TODODESCRIPTION, mToDoDescription);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
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


        // add the dateCreated to the JSON which will be written
        // if(dateAssigned == null) {
        //     Date date = new Date();
        //     this.dateAssigned = date;
        // }

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        jsonObject.put(TODODATECREATED, df.format(dateAssigned));
        // jsonObject.put(TODODATECREATED, dateCreated.toString());




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
}

