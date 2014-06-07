package org.segfault.foodme.test;

import org.segfault.foodme.AppetizerActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class AppetizerActivityTest extends ActivityInstrumentationTestCase2<AppetizerActivity> {

	public AppetizerActivityTest(Class<AppetizerActivity> activityClass) {
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