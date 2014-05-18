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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class DrinkActivity extends Activity implements ActionBar.TabListener{

	private String[] subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private ViewPager mViewPager;
	private AlertDialog.Builder dialogBuilder;
	private String strName;
	//private AppSectionsPagerAdapter mAppSectionsPagerAdapter
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get the view from activity_main.xml
		setContentView(R.layout.activity_drink);
		
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                 View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	   decorView.setSystemUiVisibility(mUIFlag);
	   
       subcategoryNames = getResources().getStringArray(R.array.test_names);
       subcategoryLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       subcategoryList = (ListView) findViewById(R.id.left_drawer);
       
      // mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

       // Set up the action bar.
       final ActionBar actionBar = getActionBar();

       // Specify that the Home/Up button should not be enabled, since there is no hierarchical
       // parent.
       actionBar.setHomeButtonEnabled(false);

       // Specify that we will be displaying tabs in the action bar.
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       

       // Add 3 tabs, specifying the tab's text and TabListener
       actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Drinks").setTabListener(this),true);
       actionBar.addTab(actionBar.newTab().setText("Appetizer").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Entree").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Dessert").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("My Orders").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Call Waiter").setTabListener(this),false);
       

       mViewPager = (ViewPager) findViewById(R.id.pager);

       // Set the adapter for the list view
       subcategoryList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.test_layout, subcategoryNames));
       
	}
	
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
   		String tabChosen = arg0.getText().toString();
		switch(tabChosen) {
		case "Home": 
			Intent homeIntent = new Intent(DrinkActivity.this,
					MainMenuActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(homeIntent); 
			break;
		case "Appetizer": 
			Intent appetizerIntent = new Intent(DrinkActivity.this,
					AppetizerActivity.class);
			appetizerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(appetizerIntent);
			break;
		case "Entree": 
			Intent entreeIntent = new Intent(DrinkActivity.this,
					EntreeActivity.class);
			entreeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(entreeIntent);
			break;
		case "Dessert": 
			Intent dessertIntent = new Intent(DrinkActivity.this,
					DrinkActivity.class);
			dessertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(dessertIntent);
			break;
		case "My Orders": break;
		case "Call Waiter":
			callWaiterPress();
			break;
		default: break;
		}
		
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	private void callWaiterPress()
	{
		dialogBuilder = new AlertDialog.Builder(this);
		final EditText txtInput = new EditText(this);
		strName = "Pizza Name: ";
		
		dialogBuilder.setTitle("Contact Waiter");
		dialogBuilder.setMessage("Would you like to contact a waiter?");
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(),"Waiter has been contacted",Toast.LENGTH_SHORT);
				
			}
		});
		AlertDialog dialogPizzaName = dialogBuilder.create();
		dialogPizzaName.show();
	}
	
}
