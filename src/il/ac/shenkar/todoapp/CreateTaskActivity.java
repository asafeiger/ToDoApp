package il.ac.shenkar.todoapp;


import il.ac.shenkar.todoapp.pickers.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.*;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class CreateTaskActivity extends Activity 
{
	private MyEventList eventList = MyEventList.getInstance(this);
	private Calendar calendar = Calendar.getInstance(); 
	private EditText editTextName;
	private EditText editTextDescription;
	private TimePickerFragment timeFragment;
	private DatePickerFragment dateFragment;
	private int year, month, day, hour, minute;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextDescription = (EditText) findViewById(R.id.edit_text_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_create_task, menu);
        return true;
    }
    
    public void onCheckReminder(View v)
    {
    	CheckBox checkBoxReminder = (CheckBox) findViewById(R.id.reminder_check_box);
    	if(checkBoxReminder.isChecked())
    	{
    		showDatePickerDialog(v);
    		showTimePickerDialog(v);
    	}
    }
    
    public void onRandomCheck(View v)
    {
    	CheckBox random = (CheckBox) findViewById(R.id.random_task_picker);
    	if(random.isChecked())
    	{	
			try
			{
				GetFromWebTask getTaskFromWeb = new GetFromWebTask();
				URL url = new URL("http://mobile1-tasks-dispatcher.herokuapp.com/task/random");
				getTaskFromWeb.execute(url);
				JSONObject jsonResponse = new JSONObject(getTaskFromWeb.get());
				editTextName.setText(jsonResponse.getString("topic"));
				editTextDescription.setText(jsonResponse.getString("description"));
			}
			
			catch(JSONException e)
			{
				e.printStackTrace();
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 
			catch (ExecutionException e) 
			{
				e.printStackTrace();
			}
    	}
    }

    public void showDatePickerDialog(View v) 
	{
	    dateFragment = new DatePickerFragment();
	    dateFragment.show(getFragmentManager(), "datePicker");
	}
    
    public void showTimePickerDialog(View v)
	{
    	timeFragment = new TimePickerFragment();
    	timeFragment.show(getFragmentManager(), "timePicker");
	}
    
    public void createTask(View v) 
	{

        EventDetails ed = new EventDetails(editTextName.getText().toString()
        	,editTextDescription.getText().toString());
        
        if(dateFragment != null)
        {
        	Bundle dateBundle = dateFragment.getBundle();
        	year = dateBundle.getInt("year");
        	month = dateBundle.getInt("month");
	    	day = dateBundle.getInt("day");
        }
        
        if(timeFragment != null)
        {
		    Bundle timeBundle = timeFragment.getBundle();
		    hour = timeBundle.getInt("hour");
		    minute = timeBundle.getInt("minute");
	        	
		    calendar.set(year, month, day, hour, minute, 0);
        }
    	
    	if((dateFragment != null) && (timeFragment != null))
    	{
    		Intent	broadCastIntent = new Intent(getApplicationContext(), ReminderBroadCastReceiver.class);
        	broadCastIntent.putExtra("id", ed.getId());
        	PendingIntent pendingIntent	= PendingIntent.getBroadcast(getApplicationContext(), (int) ed.getId(), broadCastIntent, 0);		
        	AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);									
        	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);	
        	
        	finish();
    	}
    	
    	eventList.addTaskToList(ed);
	} 
}
