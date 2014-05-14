package org.segfault.foodme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreenActivity extends Activity{

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
	}
	
}
