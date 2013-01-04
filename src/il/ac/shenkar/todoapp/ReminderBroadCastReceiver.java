package il.ac.shenkar.todoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadCastReceiver extends BroadcastReceiver
{
	private NotificationManager myAlarmManager;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
		long id = intent.getLongExtra("id", -1);
		EventDetails ed = (EventDetails) dbHandler.getEvent(id);
		Intent myIntent = new Intent(context, TaskListActivity.class);	
		PendingIntent pendingIntent	= PendingIntent.getActivity(context, (int) ed.getId(), myIntent, 0);	
		myAlarmManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);										
		
		Notification.Builder noti = new Notification.Builder(context);
		noti.setContentTitle(ed.getName());
		noti.setContentText(ed.getDescription());
		noti.setDefaults(Notification.DEFAULT_ALL);
		noti.setContentIntent(pendingIntent);
		
		Notification notification = noti.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		myAlarmManager.notify((int) ed.getId(), notification);
	}
}
