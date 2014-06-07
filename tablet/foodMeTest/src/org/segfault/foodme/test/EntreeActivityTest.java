package org.segfault.foodme.test;

import org.segfault.foodme.EntreeActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class EntreeActivityTest extends ActivityInstrumentationTestCase2<EntreeActivity> {
	
		public EntreeActivityTest(Class<EntreeActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
		TextView textview = null;
		Activity entreeActivity = null;

	//	MenuItem entree;
		protected void setUp() throws Exception {
			super.setUp();
			entreeActivity = this.getActivity();
			//checks add button text
			textview = (TextView) entreeActivity.findViewById(org.segfault.foodme.R.id.add_button);
		}
		
		public void testEntree() {
			//nothing
			assertEquals(textview.toString(), "Add to Order");
		}
		protected void tearDown() throws Exception {
			// TODO Auto-generated method stub
			super.tearDown();
		}

	}