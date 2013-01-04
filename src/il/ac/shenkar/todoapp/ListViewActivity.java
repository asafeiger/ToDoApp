package il.ac.shenkar.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class ListViewActivity extends Activity 
{
	//private MyEventList eventList;
	
	public ListViewActivity(Context context)
	{
		//eventList = MyEventList.getInstance(this);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_list_view, menu);
        return true;
    }
}
