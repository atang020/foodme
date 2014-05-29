package org.segfault.foodme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
	    								   TabletContentProvider.KEY_NAME,
	    								   TabletContentProvider.KEY_CATEGORY};
	    Cursor cursor = cr.query(TabletContentProvider.SUBCATEGORY_CONTENT_URI, projection, null, null, null);
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
                // convert String reviewDate to Date type
				Date date;
				try {
					date = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(reviewDate);
				} catch (ParseException e) {
					date = null;
					e.printStackTrace();
				}
				reviews.add(new Review (reviewId, menuItemId, reviewer, rating, reviewText, date));
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
	
	public short getRating (int index)
    {
        return reviews.get(index).getRating();
    }
	
	public String getReviewText (int index)
    {
        return reviews.get(index).getReviewText();
    }
	
	public Date getReviewDate (int index)
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
