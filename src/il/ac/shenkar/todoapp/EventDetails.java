package il.ac.shenkar.todoapp;

import android.text.format.Time;


public class EventDetails 
{
	private long id;
	private String name;
	private String description;
	private Time creationDate;
	
	public EventDetails() 
	{
		name ="";
		description = "";
		creationDate = new Time();
		creationDate.setToNow();
	}
	
	public EventDetails(String name, String description) 
	{
		super();
		this.name = name;
		this.description = description;
		this.creationDate = new Time();
		this.creationDate.setToNow();
	}
	
	public EventDetails(long id, String name, String description, String date) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		setDateFromString(date);
	}
	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public Time getCreationDate()
	{
		return creationDate;
	}
	
	public String getDateToString()
	{
		return creationDate.toString();
	}
	
	public void setDateFromString(String date)
	{
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, 8));

		int hour = Integer.parseInt(date.substring(9, 11));
		int minute = Integer.parseInt(date.substring(11, 13));
		int second = Integer.parseInt(date.substring(13, 15));

		this.creationDate.set(second, minute, hour, day, month-1, year);
	}
	
	public String getCreationDateByFormat()
	{
		return  creationDate.toString().substring(0, 4) + "/" +
				creationDate.toString().substring(4, 6) + "/" +
				creationDate.toString().substring(6, 8) + " " +
				creationDate.toString().substring(9, 11) + ":" +
				creationDate.toString().substring(11, 13) + ":" +
				creationDate.toString().substring(13, 15);
	}
}
