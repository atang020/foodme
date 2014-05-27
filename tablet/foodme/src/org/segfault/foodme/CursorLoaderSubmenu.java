package org.segfault.foodme;

import java.io.File;
import java.lang.Integer;
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
        LoaderManager.LoaderCallbacks<Cursor> {
    /*
        ArrayList<menuItem> menuItems = new ArrayList<menuItem>();
        ArrayList<Integer> itemIdList = new ArrayList<Integer>();
        ArrayList<Integer> subcategoryIdList = new ArrayList<Integer>();
        ArrayList<Double> itemPriceList = new ArrayList<Double>();
        ArrayList<String> itemPicPathList =new ArrayList<String>();
        ArrayList<String> itemNameList=new ArrayList<String>();
        ArrayList<String> itemDescriptionList=new ArrayList<String>();
    */
    ArrayList<String> subCategoryNameList = new ArrayList<String>();
    ArrayList<Integer> subCategoryIdList = new ArrayList<Integer>();
    ArrayList<Integer> categoryList = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Maybe have a gridview that displays each menu Item?

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // Grab URI corresponding with menuItem table
        Uri CONTENT_URI = TabletContentProvider.SUBCATEGORY_CONTENT_URI;
        return new CursorLoader(getActivity(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                /*
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
                */
                int category;
                int subCategoryId;
                String name;
                category = cursor.getInt(cursor.getColumnIndex("category"));
                subCategoryId = cursor.getInt(cursor.getColumnIndex("subcategory_id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                subCategoryNameList.add(name);
                subCategoryIdList.add(subCategoryId);
                categoryList.add(category);
            }
            while (cursor.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0)
    {
        // TODO Auto-generated method stub

    }
    public String getName(int index)
    {
        return subCategoryNameList.get(index);
    }
    public int getSubCategoryId(int index)
    {
        return subCategoryIdList.get(index);
    }
    public int getCategory(int index)
    {
        return categoryList.get(index);
    }
    public ArrayList<String> getSubcategoryNameList(int index)
    {
        int indices=0;
        ArrayList<String> itemsInSubCat = new ArrayList<String>();
        for(int i=0;i<subCategoryNameList.size();i++)
        {
            if(categoryList.get(i)==index)
            {
                itemsInSubCat.set(indices,subCategoryNameList.get(i));
                index++;
            }
        }
        return itemsInSubCat;
    }
}