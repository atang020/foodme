package org.segfault.foodme;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreenActivity extends Activity 
{
	// SyncAdapter constants
	public static final String AUTHORITY = "org.segfault.foodme.tabdbprovider";
	public static final String ACCOUNT_TYPE = "org.segfault.foodme.datasync";
	public static final String ACCOUNT = "default_account";
	public static final int TABLE_NUMBER = 12;
	public static Ticket ticket;
	public static ArrayList<TicketItem> orders = new ArrayList<TicketItem>();
	public static Account myAccount;
	static boolean x = false;
	ContentResolverMenuItem foodDetails;
	ContentResolverReview foodReviews;
	ContentResolverSubcategory foodList;
	BroadcastReceiver syncReceiver;
	View decorView;
	ProgressDialog dialog;
	
	// Set Duration of the Splash Screen
	long Delay = 8000;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		syncReceiver = new SyncReceiver(this);
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Get the view from splash_screen.xml
		setContentView(R.layout.splash_screen);
		
		dialog = new ProgressDialog(this);

		
		decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                View.SYSTEM_UI_FLAG_FULLSCREEN
               | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	    decorView.setSystemUiVisibility(mUIFlag);
	   
	    dialog = ProgressDialog.show(SplashScreenActivity.this, "Syncing with the database", "Please wait", true, false, null);
	    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
	    
		int titleId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
		int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
		TextView title = (TextView) dialog.findViewById(titleId);
		title.setTextColor(getResources().getColor(R.color.apptheme_color));
		View dividerBar = dialog.findViewById(dividerId);
		dividerBar.setBackgroundColor(getResources().getColor(R.color.apptheme_color));
	    // Sync adapter: create the account type and default account
	    Account myAccount = new Account ("dummyAccount", "org.segfault.foodme");
	    AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
	    accountManager.addAccountExplicitly(myAccount, null, null);
	    Bundle bundle = new Bundle();
	    
	    // Force sync if global sync settings are off
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
	    ContentResolver.requestSync (myAccount, "org.segfault.foodme.tabdbprovider", bundle);
	    
	    foodDetails = ContentResolverMenuItem.getInstance(this);
	    foodList =  ContentResolverSubcategory.getInstance(this);
	    foodReviews = ContentResolverReview.getInstance(this);
	    
	    // Insert new Ticket into server table and retrieve ticketId
	    ticket = new Ticket ();
	    new CreateTicket().execute();
	}
	
	@Override
	public void onResume() {
	    super.onResume();

	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(SyncAdapter.START_SYNC);
	    intentFilter.addAction(SyncAdapter.FINISH_SYNC);
	    registerReceiver(syncReceiver, intentFilter);  
	}
	
	public void updateMessage(boolean display)
	{
		if(display)
		{
			dialog.dismiss();
			foodDetails.update();
			foodList.update();
			foodReviews.update();
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
		}
	}
	
	private class SyncReceiver extends BroadcastReceiver {

	    private SplashScreenActivity splashScreen;

	    public SyncReceiver(SplashScreenActivity activity) {
	    	splashScreen = activity;
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (intent.getAction().equals(SyncAdapter.FINISH_SYNC)) {
	        	splashScreen.updateMessage(true);
	        }
	    }
	}
}
