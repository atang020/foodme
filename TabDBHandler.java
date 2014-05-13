/*
 * TabDBHandler
 *
 * This class hides the logic used to decide if a database needs to be created or
 * upgraded before it is opened, as well as ensure that each operation is completed
 * efficiently.
 */

// TODO: This file should be added to the android project.
// package thepackageweareusingforthisproject

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TabDBHandler extends SQLiteOpenHelper {

    // Constants for menu_item and subcategory tables
	private static final String MENU_ITEM_ID   = "menu_item_id";
	private static final String SUBCATEGORY_ID = "subcategory_id";
	private static final String NAME           = "name";
	private static final String DESCRIPTION    = "description";
	private static final String PICTURE_PATH   = "picture_path";
	private static final String PRICE          = "price";
	private static final String CATEGORY       = "category";
	private static final String DATABASE_NAME  = "tabDatabase.db";
	private static final String DB_MENU_ITEM_TABLE   = "menu_item";
	private static final String DB_SUBCATEGORY_TABLE = "subcategory";

	// SQL Statement to create new menu_item table.
	private static final String MENU_ITEM_CREATE =
			"CREATE TABLE " + DB_MENU_ITEM_TABLE + " (" +
			MENU_ITEM_ID + " INT NOT NULL, " +
			SUBCATEGORY_ID + " INT NOT NULL, " +
			NAME + " VARCHAR(100) NOT NULL, " +
			DESCRIPTION + " TEXT NOT NULL, " +
			PICTURE_PATH + " VARCHAR(256) NULL, " +
			PRICE + " DECIMAL(6,2) NOT NULL, " +
			"PRIMARY KEY (" + MENU_ITEM_ID + "), " +
			"FOREIGN KEY (" + SUBCATEGORY_ID + ") REFERENCES " + DB_SUBCATEGORY_TABLE +
			"(" + SUBCATEGORY_ID + "));";

	// SQL Statement to create new subcategory table
	private static final String SUBCATEGORY_CREATE =
			"CREATE TABLE " + DB_SUBCATEGORY_TABLE + " (" +
			SUBCATEGORY_ID + " INT NOT NULL, " +
			NAME + " VARCHAR(32) NOT NULL, " +
			CATEGORY + " INT NOT NULL, " +
			"PRIMARY KEY (" + SUBCATEGORY_ID + "));";

    // Constructor, delegates to superclass
	public DBOpenhelper(Context context, String name, CursorFactory factory,
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
		Log.w(DBOpenhelper.class.getName(), "Upgrading from bersion " +
				oldVersion + " to " + newVersion +
				", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS" + DB_MENU_ITEM_TABLE);
		db.execSQL("DROP TABLE IF EXISTS" + DB_SUBCATEGORY_TABLE);
		onCreate(db);
	}
}

