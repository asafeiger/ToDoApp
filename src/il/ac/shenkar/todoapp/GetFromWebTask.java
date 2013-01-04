package il.ac.shenkar.todoapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public	class GetFromWebTask extends AsyncTask<URL, Integer, String>
{
	private JSONObject object;
	
	@Override
	protected String doInBackground(URL... params) 
	{
		String response = "";
		URL remoteURL = params[0];
		
		try
		{
			HttpURLConnection connection = 
					(HttpURLConnection) remoteURL.openConnection();
			
			InputStream in = new BufferedInputStream(connection.getInputStream());	
			InputStreamReader inReader = new InputStreamReader(in);
			
			BufferedReader bufferedReader = new BufferedReader(inReader);
			StringBuilder respondeBuilder = new StringBuilder();
			
			for(String line = bufferedReader.readLine(); line != null;
					line = bufferedReader.readLine())
			{
				respondeBuilder.append(line);
			}
			
			response = respondeBuilder.toString();
			object = new JSONObject(response);
			
			return response;
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		catch(Exception e)
		{
			Log.e("doInBackground", e.getMessage());
		}
		
		return null;
	}

	public JSONObject getObject() 
	{
		return object;
	}
	
	
}