package org.segfault.foodme;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ContentResolverMenuItem 
{
	Context context;	
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    private static ContentResolverMenuItem ref;
    Bitmap bmp;
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
	    menuItems.clear();
	    // use cursor to insert rows from table into ArrayList
	    if (cursor.moveToFirst())
        {
			do 
			{
				int menuItemId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_ID));
				int subcategoryId = cursor.getInt(cursor.getColumnIndex(TabletContentProvider.KEY_SUBCATEGORY_ID));
				String name = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_NAME));
				String description = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_DESCRIPTION));
				String picturePath = cursor.getString(cursor.getColumnIndex(TabletContentProvider.KEY_PICTURE_PATH));
				double price = cursor.getDouble(cursor.getColumnIndex(TabletContentProvider.KEY_PRICE));
				menuItems.add(new MenuItem (menuItemId, subcategoryId, name, description, picturePath, price));
			}
            while (cursor.moveToNext());
		}
	    
	    cursor.close();
	    android.util.Log.v("CRMenuItem", "menuItem data processed");
	}
	
	public static ContentResolverMenuItem getInstance(Context context) 
	{
		if (ref == null)
		{
			ref = new ContentResolverMenuItem(context);
		}
		return ref;
	}

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
    public String[] getItemsBySubcategory (int subcategoryId)
    {
        ArrayList<String> subcategoryItems = new ArrayList<String>();
        
        for(int i=0; i < menuItems.size(); i++)
        {
            if (getSubcategoryId(i) == subcategoryId)
            {
            	subcategoryItems.add(getName(i));
            }
        }
        return subcategoryItems.toArray(new String[subcategoryItems.size()]);
    }

    // find the index from master array when taking in a name of a food
    // used to getIndex from master arrays from object in the subcategory array
    public int getIndexByName(String name)
    {
        for(int i = 0; i < menuItems.size(); i++)
        {
            if(name.equals(getName(i)))
            {
                return i;
            }
        }
        return -1;
    }

    //download from url
    void downloadFood(String picturePath)
    {
        URL picUrl = null;
        try {
            picUrl= new URL("www.jdelaney.org/uploads/"+picturePath);
            bmp = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
        } catch (Exception except) {
            Log.v("Error downloading bitmap from url", except.getMessage());
        }

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/foodImages");
        myDir.mkdirs();
        String foodName = picturePath+".jpg";
        File foodFile = new File (myDir, foodName);
        if (foodFile.exists())
        {
            foodFile.delete ();
        }
        try
        {
            FileOutputStream out = new FileOutputStream(foodFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
