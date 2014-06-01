package org.segfault.foodme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class CallWaiter extends AsyncTask<Void, Void, Void> 
{
	@Override
	protected Void doInBackground(Void... params) 
	{
		putJsonObject ("http://jdelaney.org/api/tickets", makeJson());
		return null;
	}	
	
	public JSONObject makeJson() 
	{
		JSONObject json = new JSONObject();
	    
	    try 
	    {
	    	json.put("ticket_id", SplashScreenActivity.ticket.getTicketId());
	    	json.put("call_waiter_status", 1);
	    	SplashScreenActivity.ticket.setCallWaiterStatus ((short) 1);
		} 
	    catch (JSONException e) 
	    {
			e.printStackTrace();
		}
	    return json;
	}
	
	public void putJsonObject (String uri, JSONObject jsonTicket) 
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(uri);
		InputStream inputStream;
		String result;
		
		try 
		{
			httpPut.setEntity(new StringEntity(jsonTicket.toString()));
			httpPut.setHeader("Content-type", "application/json");
			httpPut.setHeader("Accept", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpPut);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
			{
	 	    	result = convertInputStreamToString(inputStream);
	 	    	android.util.Log.v("splash", result);
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private String convertInputStreamToString(InputStream inputStream) throws IOException
	{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
        {
            result += line;
        }
        inputStream.close();
        return result;
    }	
}