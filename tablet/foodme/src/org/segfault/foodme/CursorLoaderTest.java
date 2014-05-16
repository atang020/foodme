package org.segfault.foodme;

import java.util.ArrayList;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CursorLoaderTest extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	ArrayList<menuItem> menuItems = new ArrayList<menuItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Maybe have a gridview that displays each menu Item?
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Grab URI corresponding with menuItem table
		Uri CONTENT_URI = TabletContentProvider.MENU_ITEM_CONTENT_URI;
		return new CursorLoader(this, CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor.moveToFirst()) {
			do {
				menuItem temp = new menuItem();
				temp.menuItemId = cursor.getInt(cursor.getColumnIndex("_id"));
				temp.subcategoryId = cursor.getInt(cursor.getColumnIndex("subcategoryId"));
				temp.name = cursor.getString(cursor.getColumnIndex("name"));
				temp.description = cursor.getString(cursor.getColumnIndex("description"));
				temp.picturePath = cursor.getString(cursor.getColumnIndex("picture_path"));
				temp.price = cursor.getDouble(cursor.getColumnIndex("price"));
				menuItems.add(temp);
			} while (cursor.moveToNext());
		}
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
