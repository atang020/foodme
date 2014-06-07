package org.segfault.foodme.test;

import org.segfault.foodme.DrinkActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class DrinkActivityTest extends ActivityInstrumentationTestCase2<DrinkActivity> {

	public DrinkActivityTest(Class<DrinkActivity> activityClass) {
		super(activityClass);
	}
	
	TextView textview = null;
	Activity entreeActivity = null;

	// MenuItem entree;
	protected void setUp() throws Exception {
		super.setUp();
		entreeActivity = this.getActivity();
		
		// Checks add button text
		textview = (TextView) entreeActivity.findViewById(org.segfault.foodme.R.id.add_button);
	}

	public void testEntree() {
		assertEquals(textview.toString(), "Add To Order");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}