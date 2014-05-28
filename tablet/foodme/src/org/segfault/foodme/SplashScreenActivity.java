package org.segfault.foodme;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreenActivity extends Activity {
	// SyncAdapter constants
	public static final String AUTHORITY = "org.segfault.foodme.tabdbprovider";
	public static final String ACCOUNT_TYPE = "org.segfault.foodme.datasync";
	public static final String ACCOUNT = "default_account";
	Account myAccount;
	static CursorLoaderMenuitem CLMenuItem;
	static CursorLoaderSubcategory CLSubcategory;
	
	// Set Duration of the Splash Screen
	long Delay = 8000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
	   
	    decorView.setOnTouchListener(new OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	    		Intent myIntent = new Intent(SplashScreenActivity.this,
						MainMenuActivity.class);
	    		startActivity(myIntent);
	    		return true;
	        }
	    });
	    
	    // SyncAdapter
	    // Create the account type and default account
	    Account myAccount = new Account ("dummyAccount", "org.segfault.foodme");
	    AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);
	    accountManager.addAccountExplicitly(myAccount, null, null);
	    Bundle bundle = new Bundle();
	    // Force sync if global sync settings are off
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
	    ContentResolver.requestSync (myAccount, "org.segfault.foodme.tabdbprovider", bundle);
	    
	    ArrayList<menuItem> menuItems = new ArrayList<menuItem>();
	    ContentResolver cr = getContentResolver();
	    String[] projection = new String[]{TabletContentProvider.KEY_ID, 
	    								   TabletContentProvider.KEY_SUBCATEGORY_ID,
	    								   TabletContentProvider.KEY_NAME,
	    								   TabletContentProvider.KEY_DESCRIPTION,
	    								   TabletContentProvider.KEY_PICTURE_PATH,
	    								   TabletContentProvider.KEY_PRICE};
	    Cursor cursor = cr.query(TabletContentProvider.MENU_ITEM_CONTENT_URI, projection, null, null, null);
	    if (cursor.moveToFirst())
        {
			do {
				menuItem temp = new menuItem();
				temp.menuItemId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_ID));
				temp.subcategoryId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_SUBCATEGORY_ID));
				temp.name = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_NAME));
				temp.description = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_DESCRIPTION));
				temp.picturePath = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_PICTURE_PATH));
				temp.price = cursor.getDouble(cursor.getColumnIndex(TabletContentProvider.KEY_PRICE));
                //add the individual items into the respective arrays
				menuItems.add(temp);
			}
            while (cursor.moveToNext());
		}
	    
	    System.out.println(menuItems.get(0).menuItemId);
	    System.out.println(menuItems.get(0).subcategoryId);
	    System.out.println(menuItems.get(0).name);
	    System.out.println(menuItems.get(0).description);
	    System.out.println(menuItems.get(0).picturePath);
	    System.out.println(menuItems.get(0).price);
	    
	    // Start the cursor loaders
	    CLMenuItem = new CursorLoaderMenuitem();
	    
	    //CLSubcategory = new CursorLoaderSubcategory();
	}
	
}
