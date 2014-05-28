package org.segfault.foodme;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class ContentResolverSubcategory 
{
	Context context;
	ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
	private static ContentResolverSubcategory ref;
	
	private ContentResolverSubcategory(Context context) {
		this.context = context;
	    ContentResolver cr = context.getContentResolver();
	    String[] projection = new String[]{TabletContentProvider.KEY_ID,
	    								   TabletContentProvider.KEY_NAME,
	    								   TabletContentProvider.KEY_CATEGORY};
	    Cursor cursor = cr.query(TabletContentProvider.SUBCATEGORY_CONTENT_URI, projection, null, null, null);
	    subcategories.clear();
	    // use cursor to insert rows from table into ArrayList
	    if (cursor.moveToFirst())
        {
			do 
			{
				int subcategoryId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_ID));
				String name = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_NAME));
				int category = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_CATEGORY));
				subcategories.add(new Subcategory (subcategoryId, name, category));
			}
            while (cursor.moveToNext());
		}
	    
	    cursor.close();
	    android.util.Log.v("CRMenuItem", "menuItem data processed");
	}

	public static ContentResolverSubcategory getInstance (Context context) 
	{
		if (ref == null)
		{
			ref = new ContentResolverSubcategory (context);
		}
		return ref;
	}
	
	public int getSubcategoryId (int index)
    {
       return subcategories.get(index).getSubcategoryId();
    }
	
	public String getName(int index)
    {
        return subcategories.get(index).getName();
    }
	
	public int getCategory(int index)
    {
        return subcategories.get(index).getCategory();
    }
	
	public int getSubcategoryIdByName (String name) {
		for (int i = 0; i < subcategories.size(); i++)
		{
			if (getName(i) == name)
			{
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<String> getSubcategoryNamesByCategory (int index)
    {
        ArrayList<String> subcategoryNames = new ArrayList<String>();
     
        for(int i = 0; i < subcategories.size(); i++)
        {
            if(getCategory(i) == index)
            {
                subcategoryNames.add(getName(i));
            }
        }
        return subcategoryNames;
    }
}
