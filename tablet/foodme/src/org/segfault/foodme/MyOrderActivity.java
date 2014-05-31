package org.segfault.foodme;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrderActivity extends Activity implements ActionBar.TabListener, OnItemClickListener{
	
	ListView list;
	TextView subtotal;
	Button checkout;
	private String subtotalVal;
	private String[] subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private ViewPager mViewPager;
	ArrayList<TicketItem> item = new ArrayList<TicketItem>(3);
	ArrayAdapter<String> adapter; 
	private AlertDialog.Builder dialogBuild;
	private AlertDialog.Builder dialogBuild1;
	//private AppSectionsPagerAdapter mAppSectionsPagerAdapter
	Button button;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// Get the view from activity_main.xml
		setContentView(R.layout.activity_my_order);
		
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
       		String tabChosen = tab.getText().toString();
    		switch(tabChosen) {
    		case "Home":System.out.println("hisss");
    		case "Drinks":
    		case "Appetizer":
    		case "Entree":
    		case "Dessert":
    		case "My Orders":
    		case "Call Waiter":
    		}
    		
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
      // subcategoryList.setAdapter(new ArrayAdapter<String>(this,
       //        R.layout.test_layout, subcategoryNames));
       button = (Button) findViewById(R.id.button1);
       list = (ListView) findViewById(R.id.listView1);	
  		subtotal = (TextView) findViewById(R.id.textView6);
  		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice);
  		subtotalVal = subTotal();
  		
  		for(int i = 0; i<item.size(); i++) {
  			adapter.add(item.get(i).toString());
  		}
  		
  		list.setAdapter(adapter);
  		subtotal.setText(subtotalVal);
  		
  		button.setOnClickListener(new View.OnClickListener(){
  			public void onClick(View v){
  				confirmation();
  			}
  		});
  		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
  			   public void onItemSelected(AdapterView parentView, View childView, 
  			                                                         int position, long id) 
  			   {  
  				   //none
  			   }

  			   public void onNothingSelected(AdapterView parentView) {  
  				   //none
  			   }

  			@Override
  			public void onItemClick(AdapterView<?> parent, View view,
  					int position, long id) {
  				// TODO Auto-generated method stub
  				editOrder(position); 
  				adapter.notifyDataSetChanged();
  				}  
  			});         
	}
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/


	public void onTabReselected1(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	public void onTabSelected1(Tab arg0, FragmentTransaction arg1) {

	}


	public void onTabUnselected1(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	 private void confirmation(){
     	dialogBuild1 = new AlertDialog.Builder(this);
     	LinearLayout layout = new LinearLayout(getApplicationContext());
		dialogBuild1.setTitle("Check Out to Kitchen");
		dialogBuild1.setMessage("Are you sure?");
	  	dialogBuild1.setView(layout);
		dialogBuild1.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				new SendTicketItems().execute(item);
				Toast makeText = Toast.makeText(getApplicationContext(),"Order has been sent to the kitchen", Toast.LENGTH_SHORT);
				makeText.show();
			}
	 });
	     
	    	dialogBuild1.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
				Toast makeText = Toast.makeText(getApplicationContext(),"Order has not been sent to kitchen.", Toast.LENGTH_SHORT);
				makeText.show();
			}
		});
	  }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	 
	private void editOrder(final int position) {
		dialogBuild = new AlertDialog.Builder(this);
		
		final EditText input = new EditText(this);
		input.setHint("Notes");
		final EditText quantity = new EditText(this);
		quantity.setHint("Quantity");
		//dialogBuild.setView(input);
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(input);
		layout.addView(quantity);
		dialogBuild.setView(layout);
		dialogBuild.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which)
		{
		//	int quantityNum = item.get(position).getQuantity();
		//	String notesVal = item.get(position).getNotes();
			String value = input.getText().toString();
			String quantityVal = quantity.getText().toString();
			
			try {
			//	quantityNum = Integer.parseInt(quantityVal);
			}catch(NumberFormatException e) {
				Toast makeText1 = Toast.makeText(getApplicationContext(),"Give me a valid integer", Toast.LENGTH_SHORT);
				makeText1.show();
				//quantityNum = item.get(position).getQuantity();
			}
			
			if(value.trim().length() > 0)
				//notesVal = value;
			
	//		menuItem temp = new menuItem(item.get(position).getName(), notesVal, quantityNum, item.get(position).getPrice());
			System.err.println("before removing: " + position + "item is: " + item.get(position).toString());
			adapter.remove(item.get(position).toString());
			item.remove(position);
		//	item.add(temp);
			adapter.add(item.get((item.size()-1)).toString());
			subtotalVal = subTotal();
			subtotal.setText(subtotalVal);
			Toast makeText = Toast.makeText(getApplicationContext(),"Order has been edited." + item.get(position).toString(), Toast.LENGTH_SHORT);
			makeText.show();
		}
		});
		dialogBuild.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				adapter.remove(item.get(position).toString());
				item.remove(position);
				Toast makeText = Toast.makeText(getApplicationContext(),"Deleted at Index: " + position, Toast.LENGTH_SHORT);
				makeText.show();
			}
		});
		
		
		dialogBuild.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Toast makeText = Toast.makeText(getApplicationContext(),"Canceled editing order.", Toast.LENGTH_SHORT);
			makeText.show();
		}
		});
		
		AlertDialog dialogEditName = dialogBuild.create();
		dialogEditName.show();
	}
	
	public String subTotal()
	{
		BigDecimal sum = new BigDecimal("0");
		for (int i = 0; i < item.size(); i++)
		{
	//			sum += item.get(i).getPrice() * item.get(i).getQuantity();
		}
		return "Subtotal: "+ sum;
	}
	

	public void onItemClick1(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}