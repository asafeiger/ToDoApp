package il.ac.shenkar.todoapp;

import java.util.*;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	private	static DatabaseHandler instance = null;
	
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "eventListManager";
 
    // Events table name
    private static final String TABLE_EVENTS = "events";
 
    // Events Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "date";
 
    private DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public static DatabaseHandler getInstance(Context context)
    {
    	if(instance == null)
    	{
    		instance = new DatabaseHandler(context);
    	}
    	return instance;
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addEvent(EventDetails ed)
	{
    	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
	    values.put(KEY_NAME, ed.getName()); // Event Name
	    values.put(KEY_DESC, ed.getDescription()); // Event Description
	    values.put(KEY_DATE, ed.getDateToString());  //Event Creation Time

	  //Inserting Row
	    ed.setId(db.insert(TABLE_EVENTS, null, values));
	    db.close(); // Closing database connection
	}
    
    // Getting single event
    public EventDetails getEvent(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_ID,
                KEY_NAME, KEY_DESC, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        EventDetails event = new EventDetails();
 	   	event.setId(Integer.parseInt(cursor.getString(0)));
 	   	event.setName(cursor.getString(1));
 	   	event.setDescription(cursor.getString(2));
 	   	event.setDateFromString(cursor.getString(3));
        // return Event
        return event;
    }
    
    // Getting All Events
    public ArrayList<EventDetails> getAllEvents() 
    {
    	ArrayList<EventDetails> eventList = new ArrayList<EventDetails>();
       // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;
    
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
    
       // looping through all rows and adding to list
       if (cursor.moveToLast()) 
       {
           do 
           {
        	   EventDetails event = new EventDetails();
        	   event.setId(Integer.parseInt(cursor.getString(0)));
        	   event.setName(cursor.getString(1));
        	   event.setDescription(cursor.getString(2));
        	   event.setDateFromString(cursor.getString(3));
               // Adding contact to list
               eventList.add(event);
           } while (cursor.moveToPrevious());
       }
    
       // return contact list
       return eventList;
    }

    // Getting events Count
    public int getEventsCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
    // Updating single event
    public int updateEvent(EventDetails event) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getName());
        values.put(KEY_DESC, event.getDescription());
        values.put(KEY_DATE, event.getDateToString());
     
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(event.getId()) });
    }

    // Deleting single event
    public void deleteEvent(EventDetails event) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(event.getId()) });
        db.close();
    }
}