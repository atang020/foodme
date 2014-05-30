package org.segfault.foodme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class CreateTicket extends AsyncTask<Void, Void, Void> 
{
	@Override
	protected Void doInBackground(Void... params) 
	{
		postJsonObject ("http://jdelaney.org/api/tickets", makeJson());
		return null;
	}	
	
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
	 	    	SplashScreenActivity.ticket.ticket_id = Integer.parseInt(result);
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