package org.segfault.foodme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

// Takes an ArrayList of TicketItem and sends them to the server database using http POST
public class SendTicketItems extends AsyncTask<ArrayList<TicketItem>, Void, Void> 
{
	@Override
	protected Void doInBackground(ArrayList<TicketItem>... passing) 
	{
		ArrayList<TicketItem> passed = passing[0];
		
		// Convert each TicketItem to JSONObject and send to server
		for (int index = 0; index < passed.size(); index++) {
			postJsonObject ("http://jdelaney.org/api/ticketitems", makeJson(passed.get(index)));
		}
		return null;
	}	
	
	public JSONObject makeJson(TicketItem ticketItem) 
	{
		JSONObject json = new JSONObject();
	    
	    try 
	    {
	    	json.put("ticket_id", ticketItem.getOrderId());
	    	json.put("menu_item_id", ticketItem.getMenuItemId());
	    	json.put("quantity", ticketItem.getQuantity());
	    	json.put("notes", ticketItem.getNotes());
	    	json.put("kitchen_status", ticketItem.getKitchenStatus());
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
	 	    	android.util.Log.v("order", "inserted ticket item with id: " + result + " into table");
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