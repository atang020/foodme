package org.segfault.foodme;




import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainMenuActivity extends Activity implements ActionBar.TabListener{

	private String[] subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private ViewPager mViewPager;
	//private AppSectionsPagerAdapter mAppSectionsPagerAdapter
	   
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
       
    // Create a tab listener that is called when the user changes tabs.
       ActionBar.TabListener tabListener = new ActionBar.TabListener() {
           public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
               // show the given tab
           }

           public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
               // hide the given tab
           }

           public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
               // probably ignore this event
           }
       };

       // Add 3 tabs, specifying the tab's text and TabListener
       actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("Drinks").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("Appetizer").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("Entree").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("Dessert").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("My Orders").setTabListener(tabListener));
       actionBar.addTab(actionBar.newTab().setText("Call Waiter").setTabListener(tabListener));
       

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
		case "Drinks":
		case "Appetizer":
		case "Entree":
		case "Dessert":
		case "My Orders":
		case "Call Waiter":
		}
		
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
