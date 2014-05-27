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

public class CursorLoaderSubmenu extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>
{
/*
    ArrayList<menuItem> menuItems = new ArrayList<menuItem>();
    ArrayList<Integer> itemIdList = new ArrayList<Integer>();
    ArrayList<Integer> subcategoryIdList = new ArrayList<Integer>();
    ArrayList<Double> itemPriceList = new ArrayList<Double>();
    ArrayList<String> itemPicPathList =new ArrayList<String>();
    ArrayList<String> itemNameList=new ArrayList<String>();
    ArrayList<String> itemDescriptionList=new ArrayList<String>();
*/
    ArrayList<String> keyList=new ArrayList<String>();
    ArrayList<String> valueList=new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Maybe have a gridview that displays each menu Item?
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
    {
        // Grab URI corresponding with menuItem table
        Uri CONTENT_URI = TabletContentProvider.SETTING_CONTENT_URI;
        return new CursorLoader(getActivity(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        if (cursor.moveToFirst())
        {
            do {
                String key;
                String value;
                key=cursor.getString(cursor.getColumnIndex("key"));
                value=cursor.getString(cursor.getColumnIndex("value"));
                keyList.add(key);
                valueList.add(value);
            }
            while (cursor.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0)
    {
        // TODO Auto-generated method stub

    }

    public String getKey(int index)
    {
       return keyList.get(index);
    }
    public Stirng getValue(int index)
    {
        return valueList.get(int index);
    }
}
