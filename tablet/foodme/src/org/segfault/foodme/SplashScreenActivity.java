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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreenActivity extends Activity 
{
	// SyncAdapter constants
	public static final String AUTHORITY = "org.segfault.foodme.tabdbprovider";
	public static final String ACCOUNT_TYPE = "org.segfault.foodme.datasync";
	public static final String ACCOUNT = "default_account";
	public static final int TABLE_NUMBER = 12;
	public static Ticket ticket;
	Account myAccount;
	
	// Set Duration of the Splash Screen
	long Delay = 8000;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Get the view from splash_screen.xml
		setContentView(R.layout.splash_screen);
		
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                View.SYSTEM_UI_FLAG_FULLSCREEN
               | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	    decorView.setSystemUiVisibility(mUIFlag);
	   
	    decorView.setOnTouchListener(new OnTouchListener() 
	    {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) 
	        {
	    		Intent myIntent = new Intent(SplashScreenActivity.this,
						MainMenuActivity.class);
	    		startActivity(myIntent);
	    		return true;
	        }
	    });
	    
	    // Sync adapter: create the account type and default account
	    Account myAccount = new Account ("dummyAccount", "org.segfault.foodme");
	    AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
	    accountManager.addAccountExplicitly(myAccount, null, null);
	    Bundle bundle = new Bundle();
	    
	    // Force sync if global sync settings are off
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
	    ContentResolver.requestSync (myAccount, "org.segfault.foodme.tabdbprovider", bundle);
	    
	    // Insert new Ticket into server table and retrieve ticketId
	    ticket = new Ticket ();
	    new CreateTicket().execute();
	}
	
	private class CreateTicket extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected Void doInBackground(Void... params) 
		{
			postJsonObject ("http://jdelaney.org/api/tickets", makeJson());
			return null;
		}		
	}
	
	public JSONObject makeJson() 
	{
		JSONObject json = new JSONObject();
	    
	    try 
	    {
	    	json.put("table_number", TABLE_NUMBER);
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
	 	    	ticket.ticket_id = Integer.parseInt(result);
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
