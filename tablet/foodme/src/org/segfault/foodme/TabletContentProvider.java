package org.segfault.foodme;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TabletContentProvider extends ContentProvider {

	// Constants used to differentiate between the different URI
	// requests
	private static final int MENU_ITEM_ALL = 1;
	private static final int MENU_ITEM_SINGLE = 2;
	private static final int SUBCATEGORY_ALL = 3;
	private static final int SUBCATEGORY_SINGLE = 4;
	
	private static final String KEY_ID = "_id";
	
	private static final UriMatcher uriMatcher;
	private DBOpenHelper myOpenHelper;
	
	// Populate the UriMatcher object, where a URI ending in
	// 'menuItem' will correspond to a request for all items,
	// and 'menuItem/[rowID]' represents a single row. Same is
	// done for 'subcategory' and 'subcategory/[rowID]'
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("org.segfault.foodme.tabdbprovider",
						  "menuItem", MENU_ITEM_ALL);
		uriMatcher.addURI("org.segfault.foodme.tabdbprovider",
				  		  "menuItem/#", MENU_ITEM_SINGLE);
		uriMatcher.addURI("org.segfault.foodme.tabdbprovider",
						  "subcategory", SUBCATEGORY_ALL);
		uriMatcher.addURI("org.segfault.foodme.tabdbprovider",
						  "subcategory/#", SUBCATEGORY_SINGLE);
	}
	
	public TabletContentProvider() {
		// TODO Auto-generated constructor stub
	}
	
	// Creates the content provider's database
	@Override
	public boolean onCreate() {
		// Construct the underlying database.
		myOpenHelper = new DBOpenHelper(getContext(),
				DBOpenHelper.DB_NAME, null,
				DBOpenHelper.DB_VERSION);
		
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Open a read/write database to support the transaction
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		// Used if executing a single row query
		String rowID;
		int deleteCount = 0;
		if (selection==null)
			selection = "1";
		
		// Specify the table on which to perform the query.
		switch (uriMatcher.match(uri)) {
			case MENU_ITEM_ALL:
				deleteCount = db.delete(DBOpenHelper.DB_MENU_ITEM_TABLE, 
						selection, selectionArgs);
				break;
			case MENU_ITEM_SINGLE:
				// Since this is a row query, limit the result set to the
				// passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ?
						" AND (" + selection + ')' : "");
				
				deleteCount = db.delete(DBOpenHelper.DB_MENU_ITEM_TABLE, 
						selection, selectionArgs);
				break;
			case SUBCATEGORY_ALL:
				deleteCount = db.delete(DBOpenHelper.DB_SUBCATEGORY_TABLE, 
						selection, selectionArgs);
				break;
			case SUBCATEGORY_SINGLE:
				// Since this is a row query, limit the result set to the
				// passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ?
						" AND (" + selection + ')' : "");
				
				deleteCount = db.delete(DBOpenHelper.DB_SUBCATEGORY_TABLE, 
						selection, selectionArgs);
				break;
			default: break;
		}
		
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);
		
		return deleteCount;
	}

	// Returns a string that identifies the MIME type for a 
	// Content Provider URI
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case MENU_ITEM_ALL:
				return "vnd.android.cursor.dir/vnd.segfault.foodme.menuItem";
			case MENU_ITEM_SINGLE:
				return "vnd.android.cursor.item/vnd.segfault.foodme.menuItem";
			case SUBCATEGORY_ALL:
				return "vnd.android.cursor.dir/vnd.segfault.foodme.subcategory";
			case SUBCATEGORY_SINGLE:
				return "vnd.android.cursor.item/vnd.segfault.foodme.subcategory";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, 
			String[] selectionArgs, String sortOrder) {
		
		// Open the database
		SQLiteDatabase db;
		String rowID;		// Used if executing a row query
		
		try {
			db = myOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = myOpenHelper.getReadableDatabase();
		}
		
		// Replace these with valid SQL statements if necessary
		String groupBy = null;
		String having = null;
		
		// Use an SQLite Query Builder to simplify constructing the database query
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		// Specify the table on which to perform the query.
		switch (uriMatcher.match(uri)) {
			case MENU_ITEM_ALL:
				queryBuilder.setTables(DBOpenHelper.DB_MENU_ITEM_TABLE);
				break;
			case MENU_ITEM_SINGLE:
				// Since this is a row query, limit the result set to the
				// passed in row
				rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
				queryBuilder.setTables(DBOpenHelper.DB_MENU_ITEM_TABLE);
				break;
			case SUBCATEGORY_ALL:
				queryBuilder.setTables(DBOpenHelper.DB_SUBCATEGORY_TABLE);
				break;
			case SUBCATEGORY_SINGLE:
				// Since this is a row query, limit the result set to the
				// passed in row
				rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
				queryBuilder.setTables(DBOpenHelper.DB_SUBCATEGORY_TABLE);
				break;
			default:
				break;
		}
		
		// Execute the query
		Cursor cursor = queryBuilder.query(db, projection, selection, 
				selectionArgs, groupBy, having, sortOrder);
		
		// Return the result cursor
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
