package org.segfault.foodme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

//Deals with opening the database
public class DBOpenHelper extends SQLiteOpenHelper {
	// constants for menu_item and subcategory tables
	public static final String KEY_ID = "_id";
	public static final String KEY_SUBCATEGORY_ID = "subcategory_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_PICTURE_PATH = "picture_path";
	public static final String KEY_PRICE = "price";
	public static final String KEY_CATEGORY = "category";
	
	public static final String DB_NAME = "tabDatabase.db";
	public static final String DB_MENU_ITEM_TABLE = "menu_item";
	public static final String DB_SUBCATEGORY_TABLE = "subcategory";
	
	public static final int DB_VERSION = 1;
	
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
			"PRIMARY KEY (" + KEY_SUBCATEGORY_ID + "));";
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// Called when no database exists in disk and the helper class needs
	// to create one
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MENU_ITEM_CREATE);
		db.execSQL(SUBCATEGORY_CREATE);
	}

	// Called when there is a database version mismatch meaning that the
	// version of the database on disk needs to be upgraded to the current
	// version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Log version upgrade
		Log.w(DBOpenHelper.class.getName(), "Upgrading from bersion " + 
				oldVersion + " to " + newVersion + 
				", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS" + DB_MENU_ITEM_TABLE);
		db.execSQL("DROP TABLE IF EXISTS" + DB_SUBCATEGORY_TABLE);
		onCreate(db);
	}
}
