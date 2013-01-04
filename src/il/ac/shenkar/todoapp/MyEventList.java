package il.ac.shenkar.todoapp;

import java.util.*;
import android.content.Context;

public class MyEventList
{	
	private	static MyEventList instance;

	private ArrayList<EventDetails> eventList;
	private DatabaseHandler dbHandler;

	private	MyEventList(Context context) 
	{
		eventList = new ArrayList<EventDetails>();
		dbHandler = DatabaseHandler.getInstance(context);
		
		// Initiate The List From The DataBase
		eventList = getAllEventsFromDB();
	}	

	public static MyEventList getInstance(Context context)	
	{	
		if(instance ==	null)	
		{	
			instance = new MyEventList(context);	
		}	
	return	instance;	
	}

	public int size()
	{
		return eventList.size();
	}

	public EventDetails get(int pos)
	{
		return eventList.get(pos);
	}

	public void addTaskToList(EventDetails ed)
	{
		this.addEventToDB(ed);
		eventList.add(0,ed);
	}

	public void deleteTask(int pos)
	{
		EventDetails ed = get(pos);
		this.deleteEventFromDB(ed);
		eventList.remove(ed);
	}

	public void addEventToDB(EventDetails ed)
	{
		dbHandler.addEvent(ed);
	}

	public void getEventFromDB(EventDetails ed)
	{
		dbHandler.getEvent(ed.getId());
	}

	public ArrayList<EventDetails> getAllEventsFromDB()
	{
		return dbHandler.getAllEvents();
	}

	public int getEventsCountFromDB()
	{
		return dbHandler.getEventsCount();
	}

	public void updateEventInDB(EventDetails ed)
	{
		dbHandler.updateEvent(ed);
	}

	public void deleteEventFromDB(EventDetails ed)
	{
		dbHandler.deleteEvent(ed);
	} 
}