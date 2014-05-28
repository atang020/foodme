package org.segfault.foodme;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ContentResolverMenuItem {
	Context context;
	
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    private static ContentResolverMenuItem ref;
    
	private ContentResolverMenuItem(Context context) {
		this.context = context;
	    ContentResolver cr = context.getContentResolver();
	    String[] projection = new String[]{TabletContentProvider.KEY_ID, 
	    								   TabletContentProvider.KEY_SUBCATEGORY_ID,
	    								   TabletContentProvider.KEY_NAME,
	    								   TabletContentProvider.KEY_DESCRIPTION,
	    								   TabletContentProvider.KEY_PICTURE_PATH,
	    								   TabletContentProvider.KEY_PRICE};
	    Cursor cursor = cr.query(TabletContentProvider.MENU_ITEM_CONTENT_URI, projection, null, null, null);
	    
	    // use cursor to insert rows from table into ArrayList
	    if (cursor.moveToFirst())
        {
			do {
				MenuItem temp = new MenuItem();
				temp.menuItemId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_ID));
				temp.subcategoryId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_SUBCATEGORY_ID));
				temp.name = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_NAME));
				temp.description = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_DESCRIPTION));
				temp.picturePath = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_PICTURE_PATH));
				temp.price = cursor.getDouble(cursor.getColumnIndex(TabletContentProvider.KEY_PRICE));
                //add the individual items into the respective arrays
				menuItems.add(temp);
			}
            while (cursor.moveToNext());
		}
	    
	    cursor.close();
	    android.util.Log.v("CRMenuItem", "menuItem data processed");
	}
	
	public static ContentResolverMenuItem getInstance(Context context) {
		if (ref == null)
			ref = new ContentResolverMenuItem(context);
		return ref;
	}

	// all getters and setters take from the master array with all items unless otherwise specified
	// to get index of master use the find method

    public double getPrice(int index)
    {
        return menuItems.get(index).getPrice();
    }

    public int getMenuItemId(int index)
    {
        return menuItems.get(index).getMenuItemId();
    }

    public String getPicturePath(int index)
    {
        return menuItems.get(index).getPicturePath();
    }
    
    public Bitmap getPicture(int index)
    {
    	String picturePath = getPicturePath (index);
        File imgFile = new  File(picturePath);
        
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        
        return null;
    }

    public String getName(int index)
    {
        return menuItems.get(index).getName();
    }

    public String getDescription (int index)
    {
        return menuItems.get(index).getDescription();
    }

    public int getSubcategoryId (int index)
    {
       return menuItems.get(index).getSubcategoryId();
    }

    // get all items in a subcategory when given a subcategory id
    public ArrayList<String> itemsInSubcategory (int subcategoryId)
    {
        ArrayList<String> subcategoryItems = new ArrayList<String>();
        
        for(int i=0; i< menuItems.size(); i++)
        {
            if (getSubcategoryId(i) == subcategoryId)
            {
            	subcategoryItems.add(getName(i));
            }
        }
        return subcategoryItems;
    }

    // find the index from master array when taking in a name of a food
    // used to getIndex from master arrays from object in the subcategory array
    public int getStringIndex(String name)
    {
        for(int i=0; i < menuItems.size(); i++)
        {
            if(name.equals(getName(i)))
            {
                return i;
            }
        }
        return -1;
    }
}
