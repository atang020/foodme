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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppetizerActivity extends FragmentActivity implements ActionBar.TabListener, onFoodItemSelectedListener{

	private ArrayList<String> subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private AlertDialog.Builder dialogBuilder;
	ContentResolverSubcategory subcategoryProvider;
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_appetizer);
		
		View decorView = getWindow().getDecorView();
		
	    int mUIFlag = 
                 View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	   decorView.setSystemUiVisibility(mUIFlag);
	        
	   subcategoryProvider = ContentResolverSubcategory.getInstance(this);
	   subcategoryNames = subcategoryProvider.getSubcategoryNamesByCategory(0);
       subcategoryLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       subcategoryList = (ListView) findViewById(R.id.left_drawer);

       
       // Set the adapter for the list view
       subcategoryList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.test_layout, subcategoryNames));
       subcategoryList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       subcategoryList.setOnItemClickListener(new SubcategoryItemClickListener());
       subcategoryLayout.openDrawer(Gravity.LEFT);
       
       final ActionBar actionBar = getActionBar();
       
       actionBar.setHomeButtonEnabled(false);
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       
       actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Drinks").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Appetizers").setTabListener(this),true);
       actionBar.addTab(actionBar.newTab().setText("Entrees").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Desserts").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("My Order").setTabListener(this),false);
       actionBar.addTab(actionBar.newTab().setText("Call Waiter").setTabListener(this),false);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		if(arg0.getText().toString() == "Call Waiter")
			callWaiterPress();
		else
		 subcategoryLayout.openDrawer(Gravity.LEFT);
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
   		String tabChosen = arg0.getText().toString();
		switch(tabChosen) {
		case "Home":
			Intent homeIntent = new Intent(AppetizerActivity.this,
					MainMenuActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(homeIntent);
			break;
			
		case "Drinks":
			Intent drinkIntent = new Intent(AppetizerActivity.this,
					DrinkActivity.class);
			drinkIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(drinkIntent);
			break;
			
		case "Entrees":
			Intent entreeIntent = new Intent(AppetizerActivity.this,
					EntreeActivity.class);
			entreeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(entreeIntent);
			break;
			
		case "Desserts":
			Intent dessertIntent = new Intent(AppetizerActivity.this,
					DessertActivity.class);
			dessertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(dessertIntent);
			break;
			
		case "My Order":
			Intent orderIntent = new Intent(AppetizerActivity.this,
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
			public void onClick(DialogInterface dialog, int which) {
				Toast waiterConfirm = Toast.makeText(getApplicationContext(),"A waiter has been contacted.",Toast.LENGTH_SHORT);
				new CallWaiter().execute();
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
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		dialog.show();
	}


	@Override
	public void onFoodItemSelected(int position) {
		//         ArticleFragment articleFrag = (ArticleFragment)
	    FoodDetailsFragment foodDetails = (FoodDetailsFragment)
	    		getSupportFragmentManager().findFragmentById(R.id.fooddetails_fragment);
	    // If article frag is available, we're in two-pane layout...
	
	    // Call a method in the ArticleFragment to update its content
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
