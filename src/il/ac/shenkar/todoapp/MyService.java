package il.ac.shenkar.todoapp;

import java.net.URL;

import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;

public class MyService extends IntentService 
{
	private MyEventList eventList;
	
	public MyService(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) 
	{
		try
		{
			GetFromWebTask getTaskWeb = new GetFromWebTask();
			URL url = new URL("http://mobile1-tasks-dispatcher.herokuapp.com/task/random");
			getTaskWeb.execute(url);
			JSONObject jsonObject = getTaskWeb.getObject();
			
			eventList = MyEventList.getInstance(getApplicationContext());
			
			EventDetails ed = new EventDetails(jsonObject.getString("topic"), 
					jsonObject.getString("description"));
			
			eventList.addTaskToList(ed);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
