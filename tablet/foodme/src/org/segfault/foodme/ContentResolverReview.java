package org.segfault.foodme;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class ContentResolverReview 
{	
	Context context;
	ArrayList<Review> reviews = new ArrayList<Review>();
	private static ContentResolverReview ref;
	
	private ContentResolverReview(Context context) 
	{
		this.context = context;
	    ContentResolver cr = context.getContentResolver();
	    String[] projection = new String[]{TabletContentProvider.KEY_ID,
	    								   TabletContentProvider.KEY_MENU_ITEM_ID,
	    								   TabletContentProvider.KEY_REVIEWER,
	    								   TabletContentProvider.KEY_RATING,
	    								   TabletContentProvider.KEY_REVIEW_TEXT,
	    								   TabletContentProvider.KEY_REVIEW_DATE};
	    Cursor cursor = cr.query(TabletContentProvider.REVIEW_CONTENT_URI, projection, null, null, null);
	    reviews.clear();
	    // use cursor to insert rows from table into ArrayList
	    if (cursor.moveToFirst())
        {
			do 
			{
				int reviewId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_ID));
				int menuItemId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_MENU_ITEM_ID));
				String reviewer = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_REVIEWER));
				short rating = cursor.getShort(cursor.getColumnIndex(TabletContentProvider.KEY_RATING));
				String reviewText = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_REVIEW_TEXT));
				String reviewDate = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_REVIEW_DATE));
				reviews.add(new Review (reviewId, menuItemId, reviewer, rating, reviewText, reviewDate));
			}
            while (cursor.moveToNext());
		}
	    
	    cursor.close();
	    android.util.Log.v("CRReview", "review data processed");	
	}
	
	public static ContentResolverReview getInstance(Context context)
	{
		if (ref == null)
		{
			ref = new ContentResolverReview(context);
		}
		return ref;
	}
	
	public int getReviewId (int index)
    {
        return reviews.get(index).getReviewId();
    }
	
	public int getMenuItemId (int index)
    {
        return reviews.get(index).getMenuItemId();
    }
	
	public String getReviewer (int index)
    {
        return reviews.get(index).getReviewer();
    }
	
	public double getAvgRating (int menuItemId)
    {
		double avg_rating = 0;
		double num = 0;
        for(int i = 0; i < reviews.size(); i++)
        {
            if (getMenuItemId(i) == menuItemId)
            {
            	avg_rating += (double)reviews.get(i).rating;
            	num++;
            }
        }
        if(num > 0)
        {
        	return (avg_rating/num);
        }
        else
        {
        	return -1.0;
        }
    }
	
	public String getReviewText (int index)
    {
        return reviews.get(index).getReviewText();
    }
	
	public String getReviewDate (int index)
    {
        return reviews.get(index).getReviewDate();
    }
	
	// get review for given menuItemId
    public Review getReviewByMenuItemId (int menuItemId)
    {
        for(int i = 0; i < reviews.size(); i++)
        {
            if (getMenuItemId(i) == menuItemId)
            {
            	return reviews.get(i);
            }
        }
        return null;
    }
}
