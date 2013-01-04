package il.ac.shenkar.todoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EventListBaseAdapter extends BaseAdapter
{
	
	 private MyEventList EventDetailsrrayList;
	 private Context myContext;
	 private LayoutInflater l_Inflater;
	 
	 public EventListBaseAdapter(Context context) 
	 {
		 EventDetailsrrayList = MyEventList.getInstance(context);
		 l_Inflater = LayoutInflater.from(context);
		 myContext = context;
	 }
	 
	 public int getCount() 
	 {
	  return EventDetailsrrayList.size();
	 }
	 
	 public Object getItem(int position) 
	 {
	  return EventDetailsrrayList.get(position);
	 }
	 
	 public long getItemId(int position) 
	 {
	  return position;
	 }
	 
	 public View getView(int position, View convertView, ViewGroup parent) 
	 {
		 final ViewHolder holder;
	  
		 if (convertView == null) 
		 {
			   convertView = l_Inflater.inflate(R.layout.activity_list_view, null);
			   holder = new ViewHolder();
			   holder.txt_eventName = (TextView) convertView.findViewById(R.id.name);
			   holder.txt_eventDescription = new TextView(myContext);
			   holder.done = (Button) convertView.findViewById(R.id.done_button);
			   convertView.setTag(holder);
		 }
		 
		  else 
		  {
			  holder = (ViewHolder) convertView.getTag();
		  }
	   
		  holder.txt_eventName.setText(EventDetailsrrayList.get(position).getName());
		  holder.txt_eventDescription.setText(EventDetailsrrayList.get(position).getDescription());
		  holder.eventCreationalDate = EventDetailsrrayList.get(position).getCreationDateByFormat();
		  
		  holder.done.setOnClickListener(new OnClickListener() // OnClickListener to delete item from list 
			{
			    public void onClick(View v) 
			    {
			    	int pos = (Integer) v.getTag();
			    	EventDetailsrrayList.deleteTask(pos);
			        notifyDataSetChanged();
			    }
			}); 
		  
		  holder.txt_eventName.setOnTouchListener((new View.OnTouchListener() 
		  {
			
			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				AlertDialog builder = new AlertDialog.Builder(myContext).create();
		        builder.setTitle(holder.txt_eventName.getText() + " (" + holder.eventCreationalDate + ")");
		        builder.setMessage(holder.txt_eventDescription.getText());
		        builder.setButton("OK", new DialogInterface.OnClickListener() 
		        {
		             public void onClick(DialogInterface dialog, int which)
		             {
		                 dialog.dismiss();  
		             }
		        });
		        builder.show();

				return false;
			}
		})); 
		  
		  holder.done.setTag(position);
		  
		  return convertView;
	 }
	 
	 static class ViewHolder
	 {
		 TextView txt_eventName;
		 TextView txt_eventDescription;
		 String eventCreationalDate;
	 	 Button done;
	 }
}
