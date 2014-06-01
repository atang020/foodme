package org.segfault.foodme;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends Activity implements ActionBar.TabListener{

	private ViewPager mViewPager;
	private AlertDialog.Builder dialogBuilder;
	private String strName;
	//private AppSectionsPagerAdapter mAppSectionsPagerAdapter
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// Get the view from activity_main.xml
		setContentView(R.layout.activity_main);
		
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                 View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	   decorView.setSystemUiVisibility(mUIFlag);

       // Set up the action bar.
       final ActionBar actionBar = getActionBar();

       // Specify that the Home/Up button should not be enabled, since there is no hierarchical
       // parent.
       actionBar.setHomeButtonEnabled(false);

       // Specify that we will be displaying tabs in the action bar.
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

       // Add 7 tabs, specifying the tab's text and TabListener
       actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("Drinks").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("Appetizers").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("Entrees").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("Desserts").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("My Order").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("Call Waiter").setTabListener(this));
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// Do nothing when tab is reselected
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
   		String tabChosen = arg0.getText().toString();
		switch(tabChosen) {
		case "Drinks":	
			Intent drinkIntent = new Intent(MainMenuActivity.this,
					DrinkActivity.class);
			drinkIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(drinkIntent);
			break;
			
		case "Appetizers":	
			Intent appetizerIntent = new Intent(MainMenuActivity.this,
					AppetizerActivity.class);
			appetizerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(appetizerIntent);
			break; 
			
		case "Entrees":	
			Intent entreeIntent = new Intent(MainMenuActivity.this,
					EntreeActivity.class);
			entreeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(entreeIntent);
			break;
			
		case "Desserts":	
			Intent dessertIntent = new Intent(MainMenuActivity.this,
					DessertActivity.class);
			dessertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(dessertIntent);
			break;
			
		case "My Order":
			Intent orderIntent = new Intent(MainMenuActivity.this,
					MyOrderActivity.class);
			orderIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(orderIntent);
			break;
			
		case "Call Waiter":	
			callWaiterPress();
			break;
		}
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// Do nothing when tab is unselected
	}
	
	private void callWaiterPress()
	{
		dialogBuilder = new AlertDialog.Builder(this);
		
		dialogBuilder.setTitle("Contact Waiter");
		dialogBuilder.setMessage("Would you like to contact a waiter?");
		dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Toast makeText = Toast.makeText(getApplicationContext(),"A waiter has been contacted.",Toast.LENGTH_SHORT);
				new CallWaiter().execute();
				makeText.show();
				
			}
		});
		
		dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast waiterConfirm = Toast.makeText(getApplicationContext(),"A waiter has NOT been contacted.",Toast.LENGTH_SHORT);
				new CallWaiter().execute();
				waiterConfirm.show();					
			}
		});
		
		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		dialog.show();
	}
	
	public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
        	decorView.setSystemUiVisibility(
        		View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            	| View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
	}
}