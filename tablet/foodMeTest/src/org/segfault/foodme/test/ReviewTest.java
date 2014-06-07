package org.segfault.foodme.test;

import junit.framework.TestCase;

import org.segfault.foodme.Review;

import android.app.Activity;
import android.widget.TextView;

public class ReviewTest extends TestCase {
	
		public ReviewTest(Class<Review> activityClass) {
		super();
		// TODO Auto-generated constructor stub
		}
		int reviewId = 10;
		int menuItemId = 123;
		String reviewer = "review";
		short rating = 4;
		String reviewText = "reviewText 1";
		String reviewDate = "reviewDate 1";
		Review review;
		
	//	MenuItem entree;
		protected void setUp() throws Exception {
			review = new Review(reviewId, menuItemId, reviewer, rating, reviewText, reviewDate);
		}
		
		public void testEntree() {
			//nothing
			assertEquals(review.getReviewId(), 10);
			assertEquals(review.getMenuItemId(), 123);
			assertEquals(review.getReviewer(), "review");
			assertEquals(review.getRating(), 4);
			assertEquals(review.getReviewText(), "reviewText 1");
			assertEquals(review.getReviewDate(), "reviewDate 1");
			
		}
		protected void tearDown() throws Exception {
			// TODO Auto-generated method stub
			super.tearDown();
		}

	}
