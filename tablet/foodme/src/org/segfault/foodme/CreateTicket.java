package org.segfault.foodme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

// Send HttpPost request to insert new Ticket into table and receive the id
public class CreateTicket extends AsyncTask<Void, Void, Void> 
{
	@Override
	protected Void doInBackground(Void... params) 
	{
		postJsonObject ("http://jdelaney.org/api/tickets", makeJson());
		try 
		{
			receiveTicketInfo();
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}	
	
	// Creates JSONObject to be passed into postJsonObect
	public JSONObject makeJson() 
	{
		JSONObject json = new JSONObject();
	    
	    try 
	    {
	    	json.put("table_number", SplashScreenActivity.TABLE_NUMBER);
		} 
	    catch (JSONException e) 
	    {
			e.printStackTrace();
		}
	    return json;
	}
	
	// Sends data to server for processing and sets response to id
	public void postJsonObject (String uri, JSONObject jsonTicket) 
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		InputStream inputStream;
		String result;
		
		try 
		{
			httpPost.setEntity(new StringEntity(jsonTicket.toString()));
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Accept", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
			{
	 	    	result = convertInputStreamToString(inputStream);
	 	    	SplashScreenActivity.ticket.ticketId = Integer.parseInt(result);
	 	    	android.util.Log.v("splash", "successfully created table");
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
	
	// Converts input stream to String
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
	
	public void receiveTicketInfo () throws RemoteException, IOException
	{
    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet("http://jdelaney.org/api/tickets/" + 
    			String.valueOf(SplashScreenActivity.ticket.ticketId));
    	JSONObject jsonTicket;
    	try
    	{
    		HttpResponse response = client.execute(httpGet);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if(statusCode == 200)
    		{
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while((line = reader.readLine()) != null)
    			{
    				builder.append(line);
    			}
    		} 
    		else 
    		{
    			android.util.Log.v("CreateTiket","Failed to get JSON object from server");
    		}
    	}
    	catch(ClientProtocolException e)
    	{
    		e.printStackTrace();
    	} 
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	try 
    	{
			jsonTicket = new JSONObject (builder.toString());
			SplashScreenActivity.ticket.ticketId = jsonTicket.getInt("ticket_id");
			SplashScreenActivity.ticket.tableNumber = jsonTicket.getInt("table_number");
			SplashScreenActivity.ticket.checkedOut = (short)jsonTicket.getInt("checked_out");
			SplashScreenActivity.ticket.callWaiterStatus = (short)jsonTicket.getInt("call_waiter_status");
		} 
    	catch (JSONException e) 
    	{
			e.printStackTrace();
		}
	}
}