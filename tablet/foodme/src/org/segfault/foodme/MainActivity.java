package org.segfault.foodme;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	public static final String AUTHORITY = "org.segfault.foodme.tabdbprovider";
	public static final String ACCOUNT_TYPE = "org.segfault.foodme";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// Get the view from activity_main.xml
		setContentView(R.layout.activity_fullscreen);
		
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                 View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	   decorView.setSystemUiVisibility(mUIFlag);
	   
	   // SyncAdapter setup
	   // Create the account type and default account
	   Account newAccount = new Account ("myaccount", ACCOUNT_TYPE);
	   AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
	   // If the account already exists, a warning will be logged
	   accountManager.addAccountExplicitly (newAccount, null, null);
	   // Set the sync to happen as Android deems it necessary
	   ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);
	}
}
