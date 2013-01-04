package il.ac.shenkar.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TaskListActivity extends Activity 
{
	
	private EventListBaseAdapter adapter;
	
	public void createTask(View v) 
	{
        Intent intent = new Intent(getApplicationContext(), CreateTaskActivity.class);
        startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		
		adapter = new EventListBaseAdapter(this);
		 
		final ListView lv1 = (ListView) findViewById(R.id.listV_main);
		lv1.setAdapter(adapter);
		 
		lv1.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> a, View v, int position, long id) 
		{
		Object o = lv1.getItemAtPosition(position);
		EventDetails obj_EventDetails = (EventDetails)o;
		Toast.makeText(TaskListActivity.this, "You have chosen : " + " " + obj_EventDetails.getName(), Toast.LENGTH_LONG).show();
		} 
		});
	}
	
	public void onResume()
	{
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}