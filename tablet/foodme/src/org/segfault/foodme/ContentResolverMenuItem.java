package org.segfault.foodme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.http.util.ByteArrayBuffer;

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
                //downloadFood(picturePath);
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
        /*
        //downloads the pics to the folder
        URL picUrl = null;
        URL picUrl2 = null;
        boolean picNotFound=false;
        try
        {
            picUrl= new URL("http://jdelaney.org/uploads/"+picturePath);
            bmp = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
            System.out.println("URL: " + picUrl);
            System.out.println("Fuck yeah this works");
        }
        catch (Exception except)
        {
            picNotFound=true;
            System.out.println("Failure to get pic from URL");
            Log.v("Error downloading bitmap from url", except.getMessage());
        }
        if(picNotFound==true)
        {
            try
            {
                picUrl2= new URL("http://jdelaney.org/uploads/sample.jpg");
                bmp = BitmapFactory.decodeStream(picUrl2.openConnection().getInputStream());
                System.out.println("dammit well backup works at least");
            }
            catch (Exception except)
            {
                System.out.println("Failure to get pic from URL2");
                Log.v("Error downloading bitmap from url2", except.getMessage());
            }
        }

        //puts bmp to folder pictures
        String root = Environment.DIRECTORY_PICTURES;
      */
      /*
      //  File myDir = new File(root +"/foodImages");
        //myDir.mkdirs();
        /*
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
            System.out.println("FAILURE TO WRITE TO PIC_DIRECTORY");
            e.printStackTrace();
        }
        */
/*
        //enter url wanted to download from
        URL url = new URL("http://jdelaney.org/uploads/sample.jpg");
        File file = new File(fileName);
        //specify connection
        URLConnection ucon = url.openConnection();
        //specift inout stream
        InputStream is = ucon.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        //
        ByteArrayBuffer baf = new ByteArrayBuffer(50);
        int current = 0;
        while ((current = bis.read()) != -1)
        {
            baf.append((byte) current);
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baf.toByteArray());
        fos.close();
        catch (IOException e)
        {
        Log.d("ImageManager", "Error: " + e);
        }*/
    }
}
