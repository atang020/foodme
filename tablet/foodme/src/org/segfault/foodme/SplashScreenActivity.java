package org.segfault.foodme;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
	    new CallWaiter().execute();
	}
}
