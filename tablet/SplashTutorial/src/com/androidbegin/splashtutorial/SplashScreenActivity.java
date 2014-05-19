package com.androidbegin.splashtutorial;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;

public class SplashScreenActivity extends Activity {

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
		
	    int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

	   decorView.setSystemUiVisibility(mUIFlag);
	   
		// Create a Timer
		Timer RunSplash = new Timer();

		// Task to do when the timer ends
		TimerTask ShowSplash = new TimerTask() {
			@Override
			public void run() {
				// Close SplashScreenActivity.class
				finish();

				// Start MainActivity.class
				Intent myIntent = new Intent(SplashScreenActivity.this,
						MainActivity.class);
				startActivity(myIntent);
			}
		};

		// Start the timer
		RunSplash.schedule(ShowSplash, Delay);
	}
}
