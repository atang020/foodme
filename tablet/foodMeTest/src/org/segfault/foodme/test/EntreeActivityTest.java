package org.segfault.foodme.test;

import org.segfault.foodme.EntreeActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class EntreeActivityTest extends ActivityInstrumentationTestCase2<EntreeActivity> {
	
	public EntreeActivityTest(Class<EntreeActivity> activityClass) {
		super(activityClass);
	}

	Activity entreeActivity = null;
	
	// Names for buttons on details page
	TextView textview = null;
	TextView textview2 = null;
	TextView textview3 = null;

	protected void setUp() throws Exception {
		super.setUp();
		entreeActivity = this.getActivity();

		// Store button names
		textview = (TextView) entreeActivity.findViewById(org.segfault.foodme.R.id.add_button);
		textview2 = (TextView) entreeActivity.findViewById(org.segfault.foodme.R.id.submit_button);
		textview3 = (TextView) entreeActivity.findViewById(org.segfault.foodme.R.id.get_review);
	}

	public void testEntree() {
		// Make sure buttons have correct names
		assertEquals(textview.toString(), "Add To Order");
		assertEquals(textview2.toString(), "Submit Rating");
		assertEquals(textview3.toString(), "Read Reviews");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
}