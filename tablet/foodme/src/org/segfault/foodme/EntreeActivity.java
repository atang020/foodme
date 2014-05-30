package org.segfault.foodme;





import java.util.ArrayList;

import org.segfault.foodme.FoodItemFragment.onFoodItemSelectedListener;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class EntreeActivity extends FragmentActivity implements ActionBar.TabListener, onFoodItemSelectedListener{

	private ArrayList<String> subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private ViewPager mViewPager;
	private AlertDialog.Builder dialogBuilder;
	private String[] entreeTest = new String[5];
	ContentResolverSubcategory subcategoryProvider;
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_entree);
			
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                 View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	   decorView.setSystemUiVisibility(mUIFlag);
	   
	   subcategoryProvider = ContentResolverSubcategory.getInstance(this);
	   
	   subcategoryNames = subcategoryProvider.getSubcategoryNamesByCategory(20);
       subcategoryLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       subcategoryList = (ListView) findViewById(R.id.left_drawer);
       
       
       // Set the adapter for the list view
       subcategoryList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.test_layout, subcategoryNames));
       subcategoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       subcategoryList.setOnItemClickListener(new SubcategoryItemClickListener());
       
       subcategoryLayout.openDrawer(Gravity.LEFT);

       // Set up the action bar.
       final ActionBar actionBar = getActionBar();

       // Specify that the Home/Up button should not be enabled, since there is no hierarchical
       // parent.
       actionBar.setHomeButtonEnabled(false);

       // Specify that we will be displaying tabs in the action bar.
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       
       // Add 3 tabs, specifying the tab's text and TabListener
       actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Drinks").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Appetizers").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Entrees").setTabListener(this),true);
       actionBar.addTab(actionBar.newTab().setText("Desserts").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("My Order").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Call Waiter").setTabListener(this),false);

       subcategoryList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.test_layout, subcategoryNames));    
	}

	

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		if(arg0.getText().toString() == "Call Waiter")
		{
			callWaiterPress();
		}
		else
		{
		 subcategoryLayout.openDrawer(Gravity.LEFT);
		}
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
   		String tabChosen = arg0.getText().toString();
		switch(tabChosen) {
		case "Home": 
			Intent homeIntent = new Intent(EntreeActivity.this,
					MainMenuActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(homeIntent);
			break;
		case "Drinks":
			Intent drinkIntent = new Intent(EntreeActivity.this,
					DrinkActivity.class);
			drinkIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(drinkIntent);
			break;
		case "Appetizers":
			Intent appetizerIntent = new Intent(EntreeActivity.this,
					AppetizerActivity.class);
			appetizerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(appetizerIntent);
			break;
		case "Desserts":
			Intent dessertIntent = new Intent(EntreeActivity.this,
					DessertActivity.class);
			dessertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(dessertIntent);
			break;
		case "My Order":
			break;
		case "Call Waiter":
			callWaiterPress();
			break;
		}
		
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	private void callWaiterPress()
	{
		dialogBuilder = new AlertDialog.Builder(this);
		
		dialogBuilder.setTitle("Contact Waiter");
		dialogBuilder.setMessage("Would you like to contact a waiter?");
		dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast waiterConfirm = Toast.makeText(getApplicationContext(),"A waiter has been contacted.",Toast.LENGTH_SHORT);
				waiterConfirm.show();
			}
		});
		dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog dialog = dialogBuilder.create();
		dialog.show();
	}

	@Override
	public void onFoodItemSelected(int position) {
	    FoodDetailsFragment foodDetails = (FoodDetailsFragment)
	    		getSupportFragmentManager().findFragmentById(R.id.fooddetails_fragment);
	    foodDetails.updateFoodDetails(position);	
	}
	
	private class SubcategoryItemClickListener implements ListView.OnItemClickListener{
		
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	selectSubcategory(position);
        }
	}

	public void selectSubcategory(int position){
		FoodItemFragment subcategoryChosen = new FoodItemFragment();
		Bundle args = new Bundle();
		int subcatID = subcategoryProvider.getSubcategoryIdByName(subcategoryNames.get(position));
		args.putInt(subcategoryChosen.ARG_SUBCATEGORY_NUMBER, subcatID);
		System.out.println(subcatID);
		subcategoryChosen.setArguments(args);
	    getSupportFragmentManager().beginTransaction()
	                   .replace(R.id.fooditem_fragment, subcategoryChosen)
	                   .commit();

	    // Highlight the selected item, update the title, and close the drawer
	    subcategoryList.setItemChecked(position, true);
	    onFoodItemSelected(-1);
	    subcategoryLayout.closeDrawer(Gravity.LEFT);
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
