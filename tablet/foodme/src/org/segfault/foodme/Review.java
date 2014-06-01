package org.segfault.foodme;

import java.util.Date;

public class Review 
{	
	int reviewId;
	int menuItemId;
	String reviewer;
	short rating;
	String reviewText;
	String reviewDate;
	
	public Review(){}
	
	public Review (int reviewId, int menuItemId, String reviewer, short rating, 
			String reviewText, String reviewDate) {
		this.reviewId = reviewId;
		this.menuItemId = menuItemId;
		this.reviewer = reviewer;
		this.rating = rating;
		this.reviewText = reviewText;
		this.reviewDate = reviewDate;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public short getRating() {
		return rating;
	}

	public void setRating(short rating) {
		this.rating = rating;
	}
	
	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	
}
