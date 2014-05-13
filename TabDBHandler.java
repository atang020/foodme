/*
 * TabletDBAdapter
 * This class contains functionality for creating a table in the SQLite database
 * stored locally on the Android device.  It uses a SQLiteOpenHelper to hide the
 * logic used to decide if the database needs to be created or upgraded before it
 * is opened. Includes methods for opening and closing the database, and inserting,
 * updating, and deleting items from the tables.
 *
 * NOTE: A SyncAdapter will be implemented to ensure asynchronous data transfer
 *       between the tablet and the server.
 *
 * NOTE: Name of class was changed from TabDBHandler to TabletDBAdapter.  I'm afraid
 *       to try and change the name of the file...don't know how git will react.
 */


//package org.ourpackagename;

import java.math.BigDecimal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TabletDBAdapter {
	// constants for menu_item and subcategory tables
	public static final String KEY_MENU_ITEM_ID = "menu_item_id";
	public static final String KEY_SUBCATEGORY_ID = "subcategory_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_PICTURE_PATH = "picture_path";
	public static final String KEY_PRICE = "price";
	public static final String KEY_CATEGORY = "category";

	private static final String DB_NAME = "tabDatabase.db";
	private static final String DB_MENU_ITEM_TABLE = "menu_item";
	private static final String DB_SUBCATEGORY_TABLE = "subcategory";

	private static final int DB_VERSION = 1;

	// SQL Statement to create new menu_item table.
	private static final String MENU_ITEM_CREATE =
			"CREATE TABLE " + DB_MENU_ITEM_TABLE + " (" +
			KEY_MENU_ITEM_ID + " INT NOT NULL, " +
			KEY_SUBCATEGORY_ID + " INT NOT NULL, " +
			KEY_NAME + " VARCHAR(100) NOT NULL, " +
			KEY_DESCRIPTION + " TEXT NOT NULL, " +
			KEY_PICTURE_PATH + " VARCHAR(256) NULL, " +
			KEY_PRICE + " DECIMAL(6,2) NOT NULL, " +
			"PRIMARY KEY (" + KEY_MENU_ITEM_ID + "), " +
			"FOREIGN KEY (" + KEY_SUBCATEGORY_ID + ") REFERENCES " + DB_SUBCATEGORY_TABLE +
			"(" + KEY_SUBCATEGORY_ID + "));";

	// SQL Statement to create new subcategory table
	private static final String SUBCATEGORY_CREATE =
			"CREATE TABLE " + DB_SUBCATEGORY_TABLE + " (" +
			KEY_SUBCATEGORY_ID + " INT NOT NULL, " +
			KEY_NAME + " VARCHAR(32) NOT NULL, " +
			KEY_CATEGORY + " INT NOT NULL, " +
			"PRIMARY KEY (" + KEY_SUBCATEGORY_ID + "));";

	private final Context context;

	private DBOpenHelper DBHelper;
	private SQLiteDatabase db;

	public TabletDBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
	}

	// Deals with opening the database
	private static class DBOpenHelper extends SQLiteOpenHelper {

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

	// Opens the database
	public TabletDBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// Closes the database
	public void close() {
		DBHelper.close();
	}

	// Insert a menuItem into the database
	public long insertMenuItem (int menuItemId, int subcategoryId, String name,
								String description, String picturePath, BigDecimal price) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_MENU_ITEM_ID, menuItemId);
		initialValues.put(KEY_SUBCATEGORY_ID, subcategoryId);
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_DESCRIPTION, description);
		initialValues.put(KEY_PICTURE_PATH, picturePath);
		initialValues.put(KEY_PRICE, price);
		return db.insert(DB_MENU_ITEM_TABLE, null, initialValues);
	}

	// Insert a subcategory into the database
	public long insertSubcategoryItem (int subcategory_id, String name, int category) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SUBCATEGORY_ID, subcategory_id);
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_CATEGORY, category);
		return db.insert(DB_SUBCATEGORY_TABLE, null, initialValues);
	}

	// Retrieves a particular menuItem
	public Cursor getMenuItem (int menuItemId) throws SQLException {
		Cursor cursor = db.query(true, DB_MENU_ITEM_TABLE,new String[] {KEY_MENU_ITEM_ID,
				KEY_SUBCATEGORY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_PICTURE_PATH,
				KEY_PRICE}, KEY_MENU_ITEM_ID + "=" + menuItemId,
				null, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	//TODO: getSubcategoryItem
	//TODO: updateMenuItem
	//TODO: updateSubcategoryItem
}

