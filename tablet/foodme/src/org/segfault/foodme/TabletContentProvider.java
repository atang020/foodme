package org.segfault.foodme;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class TabletContentProvider extends ContentProvider 
{
	public static final String AUTHORITY = "org.segfault.foodme.tabdbprovider";
	
	// content URIs
	public static final Uri MENU_ITEM_CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/menuItem");
	public static final Uri SUBCATEGORY_CONTENT_URI =
			Uri.parse("content://" + AUTHORITY + "/subcategory");
	public static final Uri REVIEW_CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/review");
	
	// table name constants corresponding to names in tables
	public static final String DB_NAME = "tabDatabase.db";
	public static final String DB_MENU_ITEM_TABLE = "menu_item";
	public static final String DB_SUBCATEGORY_TABLE = "subcategory";
	public static final String DB_REVIEW_TABLE = "review";
	public static int DB_VERSION = 1;
	
	// constants for menu_item, subcategory, and setting tables
	public static final String KEY_ID = "_id";
	public static final String KEY_SUBCATEGORY_ID = "subcategory_id";
	public static final String KEY_MENU_ITEM_ID = "menu_item_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_PICTURE_PATH = "picture_path";
	public static final String KEY_PRICE = "price";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_VALUE = "value";
	public static final String KEY_REVIEWER = "reviewer";
	public static final String KEY_RATING = "rating";
	public static final String KEY_REVIEW_TEXT = "review_text";
	public static final String KEY_REVIEW_DATE = "review_date";
	
	// constants corresponding to either an entire table or a single row
	// used for uriMatcher
	private static final int MENU_ITEM = 1;
	private static final int MENU_ITEM_ID = 2;
	private static final int SUBCATEGORY = 3;
	private static final int SUBCATEGORY_ID = 4;
	private static final int REVIEW = 5;
	private static final int REVIEW_ID = 6;
	
	private static final UriMatcher uriMatcher;
	private DBOpenHelper myOpenHelper;
	
	// populate the UriMatcher object, where a URI ending in 'menuItem' will 
	// correspond to a request for all items, and 'menuItem/[rowID]' represents 
	// a single row, etc
	static 
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "menuItem", MENU_ITEM);
		uriMatcher.addURI(AUTHORITY, "menuItem/#", MENU_ITEM_ID);
		uriMatcher.addURI(AUTHORITY, "subcategory", SUBCATEGORY);
		uriMatcher.addURI(AUTHORITY, "subcategory/#", SUBCATEGORY_ID);
		uriMatcher.addURI(AUTHORITY, "review", REVIEW);
		uriMatcher.addURI(AUTHORITY, "review/#", REVIEW_ID);
	}
	
	public TabletContentProvider() {}
	
	// Creates the content provider's database
	@Override
	public boolean onCreate() 
	{
		// Construct the underlying database.
		myOpenHelper = new DBOpenHelper(getContext(), DB_NAME, null,DB_VERSION);
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, 
			String[] selectionArgs, String sortOrder) 
	{
		// Used if executing a single row query
		String rowID;
		
		// Open the database
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		
		// Replace these with valid SQL statements if necessary
		String groupBy = null;
		String having = null;
		
		// Use an SQLite Query Builder to simplify constructing the database query
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		// Specify the table on which to perform the query.
		switch (uriMatcher.match(uri)) 
		{
			case MENU_ITEM:
				queryBuilder.setTables(DB_MENU_ITEM_TABLE);
				break;
			case MENU_ITEM_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
				queryBuilder.setTables(DB_MENU_ITEM_TABLE);
				break;
			case SUBCATEGORY:
				queryBuilder.setTables(DB_SUBCATEGORY_TABLE);
				break;
			case SUBCATEGORY_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
				queryBuilder.setTables(DB_SUBCATEGORY_TABLE);
				break;
			case REVIEW:
				queryBuilder.setTables(DB_REVIEW_TABLE);
				break;
			case REVIEW_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
				queryBuilder.setTables(DB_REVIEW_TABLE);
				break;
			default: break;
		}
		
		// Execute the query
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, 
				groupBy, having, sortOrder);
		
		// Return the result cursor
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		// Open a read/write database to support the transaction
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		
		// Used if executing a single row query
		String rowID;
		int deleteCount = 0;
		
		// A selection value of 1 indicates to delete the entire table
		if (selection==null)
			selection = "1";
		
		// Specify the table (and possibly row)on which to perform the deletion.
		switch (uriMatcher.match(uri)) 
		{
			case MENU_ITEM:
				deleteCount = db.delete(DB_MENU_ITEM_TABLE, 
						selection, selectionArgs);
				break;
			case MENU_ITEM_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				deleteCount = db.delete(DB_MENU_ITEM_TABLE, 
						selection, selectionArgs);
				break;
			case SUBCATEGORY:
				deleteCount = db.delete(DB_SUBCATEGORY_TABLE, 
						selection, selectionArgs);
				break;
			case SUBCATEGORY_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				deleteCount = db.delete(DB_SUBCATEGORY_TABLE, 
						selection, selectionArgs);
				break;
			case REVIEW:
				deleteCount = db.delete(DB_REVIEW_TABLE, 
						selection, selectionArgs);
				break;
			case REVIEW_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				deleteCount = db.delete(DB_REVIEW_TABLE, 
						selection, selectionArgs);
				break;
			default: break;
		}
		
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);
		
		return deleteCount;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		// Open a read/write database to support the transaction
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		long id = -1;
		Uri insertedId = null;
		String nullColumnHack = null;
		
		// Insert the values into the table
		switch (uriMatcher.match(uri)) 
		{
			case MENU_ITEM:
				id = db.insert(DB_MENU_ITEM_TABLE, nullColumnHack, values);
				break;
			case SUBCATEGORY:
				id = db.insert(DB_SUBCATEGORY_TABLE, nullColumnHack, values);
				break;
			case REVIEW:
				id = db.insert(DB_REVIEW_TABLE, nullColumnHack, values);
				break;
			default: break;
		}
		
		// Construct and return the URI of the newly inserted row
		if (id > -1) 
		{
			switch (uriMatcher.match(uri)) 
			{
				case MENU_ITEM:
					insertedId = ContentUris.withAppendedId(MENU_ITEM_CONTENT_URI, id);
					break;
				case SUBCATEGORY:
					insertedId = ContentUris.withAppendedId(SUBCATEGORY_CONTENT_URI, id);
					break;
				case REVIEW:
					insertedId = ContentUris.withAppendedId(REVIEW_CONTENT_URI, id);
					break;
			}
			// Notify any observers of the change in the data set
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		} 
		else 
		{
			return null;
		}
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) 
	{
		// Open a read/write database to support the transaction
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		// Used if executing a single row query
		String rowID;
		int updateCount = 0;
		// Specify the table on which to perform the query.
		switch (uriMatcher.match(uri)) 
		{
			case MENU_ITEM:
				updateCount = db.update(DB_MENU_ITEM_TABLE, 
						values, selection, selectionArgs);
				break;
			case MENU_ITEM_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				updateCount = db.update(DB_MENU_ITEM_TABLE, 
						values, selection, selectionArgs);
				break;
			case SUBCATEGORY:
				updateCount = db.update(DB_SUBCATEGORY_TABLE, 
						values, selection, selectionArgs);
				break;
			case SUBCATEGORY_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				updateCount = db.update(DB_SUBCATEGORY_TABLE, 
						values, selection, selectionArgs);
				break;
			case REVIEW:
				updateCount = db.update(DB_REVIEW_TABLE, 
						values, selection, selectionArgs);
				break;
			case REVIEW_ID:
				// Since this is a row query, limit the result set to the passed in row
				rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID 
						+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
				updateCount = db.update(DB_REVIEW_TABLE, 
						values, selection, selectionArgs);
				break;
			default: break;
		}
		
		// Notify any observers of the change in the data set
		getContext().getContentResolver().notifyChange(uri, null);
		
		return updateCount;
	}
	
	// Returns a string that identifies the MIME type for a Content Provider URI
	@Override
	public String getType(Uri uri) 
	{
		switch (uriMatcher.match(uri)) 
		{
			case MENU_ITEM:
				return "vnd.android.cursor.dir/vnd.segfault.foodme.menuItem";
			case MENU_ITEM_ID:
				return "vnd.android.cursor.item/vnd.segfault.foodme.menuItem";
			case SUBCATEGORY:
				return "vnd.android.cursor.dir/vnd.segfault.foodme.subcategory";
			case SUBCATEGORY_ID:
				return "vnd.android.cursor.item/vnd.segfault.foodme.subcategory";
			case REVIEW:
				return "vnd.android.cursor.dir/vnd.segfault.foodme.setting";
			case REVIEW_ID:
				return "vnd.android.cursor.item/vnd.segfault.foodme.setting";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	// Deals with creating and opening the database
	private static class DBOpenHelper extends SQLiteOpenHelper 
	{			
		// SQL Statement to create new menu_item table.
		private static final String MENU_ITEM_CREATE = 
				"CREATE TABLE " + DB_MENU_ITEM_TABLE + " (" + 
				KEY_ID + " INT NOT NULL, " +
				KEY_SUBCATEGORY_ID + " INT NOT NULL, " +
				KEY_NAME + " VARCHAR(100) NOT NULL, " +
				KEY_DESCRIPTION + " TEXT NOT NULL, " + 
				KEY_PICTURE_PATH + " VARCHAR(256) NULL, " +
				KEY_PRICE + " DECIMAL(6,2) NOT NULL, " +
				"PRIMARY KEY (" + KEY_ID + "), " +
				"FOREIGN KEY (" + KEY_SUBCATEGORY_ID + ") REFERENCES " + DB_SUBCATEGORY_TABLE +
				"(" + KEY_SUBCATEGORY_ID + "));";
		
		// SQL Statement to create new subcategory table
		private static final String SUBCATEGORY_CREATE =
				"CREATE TABLE " + DB_SUBCATEGORY_TABLE + " (" +
				KEY_ID + " INT NOT NULL, " +
				KEY_NAME + " VARCHAR(32) NOT NULL, " +
				KEY_CATEGORY + " INT NOT NULL, " +
				"PRIMARY KEY (" + KEY_ID + "));";
				
		// SQL Statement to create new setting table
		private static final String REVIEW_CREATE =
				"CREATE TABLE " + DB_REVIEW_TABLE + " (" +
				KEY_ID + " INT NOT NULL, " +
				KEY_MENU_ITEM_ID + " INT NOT NULL, " +
				KEY_REVIEWER + " VARCHAR(45) NULL, " +
				KEY_RATING + " TINYINT NOT NULL, " +
				KEY_REVIEW_TEXT + " TEXT NULL, " +
				KEY_REVIEW_DATE + " VARCHAR(45), " +
				"PRIMARY KEY (" + KEY_ID + ")," +
				"FOREIGN KEY (" + KEY_MENU_ITEM_ID + ") REFERENCES " + DB_MENU_ITEM_TABLE +
				"(" + KEY_MENU_ITEM_ID + "));";
		
		public DBOpenHelper(Context context, String name, CursorFactory factory,
				int version) 
		{
			super(context, name, factory, version);
		}

		// Called when no database exists in disk and the helper class needs
		// to create one
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL(MENU_ITEM_CREATE);
			db.execSQL(SUBCATEGORY_CREATE);
			db.execSQL(REVIEW_CREATE);
		}

		// Called when there is a database version mismatch meaning that the version 
		// of the database on disk needs to be upgraded to the current version
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			// Log version upgrade
			Log.w(DBOpenHelper.class.getName(), "Upgrading from version " + 
					oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS" + DB_MENU_ITEM_TABLE);
			db.execSQL("DROP TABLE IF EXISTS" + DB_SUBCATEGORY_TABLE);
			db.execSQL("DROP TABLE IF EXISTS" + DB_REVIEW_TABLE);
			onCreate(db);
		}
	}
}
