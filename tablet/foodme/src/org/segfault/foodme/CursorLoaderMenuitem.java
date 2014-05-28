package org.segfault.foodme;

import java.io.File;
import java.util.ArrayList;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class CursorLoaderMenuitem extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	ArrayList<menuItem> menuItems = new ArrayList<menuItem>();
    ArrayList<Integer> itemIdList = new ArrayList<Integer>();
    ArrayList<Integer> subcategoryIdList = new ArrayList<Integer>();
    ArrayList<Double> itemPriceList = new ArrayList<Double>();
    ArrayList<String> itemPicPathList = new ArrayList<String>();
    ArrayList<String> itemNameList = new ArrayList<String>();
    ArrayList<String> itemDescriptionList = new ArrayList<String>();

    @Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
    {
		// Grab URI corresponding with menuItem table
		Uri CONTENT_URI = TabletContentProvider.MENU_ITEM_CONTENT_URI;
		return new CursorLoader(getActivity(), CONTENT_URI, null, null, null, null);
		
    }

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		
		if (cursor.moveToFirst())
        {
			do {
				android.util.Log.v("cursor", "onloadfinished");
				menuItem temp = new menuItem();
				temp.menuItemId = cursor.getInt(cursor.getColumnIndex("_id"));
				temp.subcategoryId = cursor.getInt(cursor.getColumnIndex("subcategoryId"));
				temp.name = cursor.getString(cursor.getColumnIndex("name"));
				temp.description = cursor.getString(cursor.getColumnIndex("description"));
				temp.picturePath = cursor.getString(cursor.getColumnIndex("picture_path"));
				temp.price = cursor.getDouble(cursor.getColumnIndex("price"));
                //add the individual items into the respective arrays
				menuItems.add(temp);
                itemIdList.add(temp.menuItemId);
                subcategoryIdList.add(temp.subcategoryId);
                itemNameList.add(temp.name);
                itemPriceList.add(temp.price);
                itemPicPathList.add(temp.picturePath);
                itemDescriptionList.add(temp.description);
                System.out.println("menuitemid: " + temp.menuItemId);
			}
            while (cursor.moveToNext());
			System.out.println(itemPriceList.size());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0)
    {
		// TODO Auto-generated method stub
		
	}

//all getters and setters take from the master array with all items unless otherwise specified\
//to get index of master use the find method

    public double getPrice(int index)
    {
        return itemPriceList.get(index);
    }

    public int getItemId(int index)
    {
        return itemIdList.get(index);
    }

    public int getMenuItemId(int index)
    {
        return itemIdList.get(index);
    }

    public String getPicPath(int index)
    {
        return itemPicPathList.get(index);
    }

    public Bitmap getPicture(String picturePath)
    {
        File imgFile = new  File(picturePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    public String getName(int index)
    {
        return itemNameList.get(index);
    }

    public String getDescription (int index)
    {
        return itemDescriptionList.get(index);
    }

    public int getSubMenuId(int index)
    {
       return subcategoryIdList.get(index);
    }


    //get all items in a subcategory when given a subcategory id
    public ArrayList<String> itemsInSubCategory(int subCatNum)
    {
        ArrayList<String> subcatItems=new ArrayList<String>();
        int indices=0;
        for(int i=0; i<menuItems.size();i++)
        {
            if(getSubMenuId(i)==subCatNum)
            {
            	String test = subcatItems.get(indices);
            	test = itemNameList.get(i);
            	subcatItems.set(indices, test);
              // subcatItems[indices]=itemNameList.get(i);
               indices++;
            }
        }
        return subcatItems;
    }

    //find the index from master array when taking in a name of a food
    //used to getIndex from master arrays from object in the subcategory array
    public int getStringIndex(String name)
    {
        for(int i=0;i<itemNameList.size();i++)
        {
            if(name.equals(itemNameList.get(i)))
            {
                return i;
            }
        }
        return -1;
    }
}