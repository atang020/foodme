package org.segfault.foodme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainMenuActivity extends Activity {

	private String[] subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
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

       
       // Set the adapter for the list view
       subcategoryList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.test_layout, subcategoryNames));
	}
}
