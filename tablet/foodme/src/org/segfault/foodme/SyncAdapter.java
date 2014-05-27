package org.segfault.foodme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.JsonReader;

// Handles the transfer of data between the server and our local db
public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String AUTHORITY = "org.segfault.food.tabdbprovider";
	private static final String PREFIX = "content://" + AUTHORITY;

	// Contains our content resolver instance
	ContentResolver myContentResolver;
	
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		// Get an instance of the content resolver from incoming Context
		myContentResolver = context.getContentResolver();
	}

	// This constructor maintains compatibility with Android 3.0 and later
	public SyncAdapter(Context context, boolean autoInitialize,
			boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		myContentResolver = context.getContentResolver();
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		try {
			deleteMenuItems(provider);
			insertMenuItems(provider);
			deleteSubcategories(provider);
			insertSubcategories(provider);

			deleteSettings(provider);
			insertSettings(provider);
			deleteReviews(provider);
			insertReviews(provider);

		} catch (RemoteException | IOException e) {
			syncResult.hasHardError();
		}
	}
	
	// Insert data into menuItems table
	private void insertMenuItems(ContentProviderClient contentProviderClient)
		throws RemoteException, IOException {

		// TODO Access server

		
		URL url = new URL("http", "jdelaney.org", 8080,"/api/menuItems");
		URLConnection conn = url.openConnection();
		
		try (
			BufferedReader bufReader = new BufferedReader (
				new InputStreamReader (conn.getInputStream(), "UTF-8"));
			JsonReader reader = new JsonReader(bufReader)
		) {
			reader.beginArray();
			
			int menuItemId = -1;
			int subcategoryId = -1;
			String itemName = null;
			String description = null;
			String picturePath = null;
			double price = -1;
			
			while (reader.hasNext()) {
				reader.beginObject();
				
				while (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("menu_item_id")) {
						menuItemId = reader.nextInt();
					} else if (name.equals("subcategory_id")) {
						subcategoryId = reader.nextInt();
					} else if (name.equals("name")) {
						itemName = reader.nextString();
					} else if (name.equals("description")) {
						description = reader.nextString();
					} else if (name.equals("picture_path")) {
						picturePath = reader.nextString();
					} else if (name.equals("price")) {
						price = reader.nextDouble();
					}
				}
				
				reader.endObject();
				
				ContentValues contentValues = new ContentValues();
				contentValues.put(TabletContentProvider.KEY_ID, menuItemId);
				contentValues.put(TabletContentProvider.KEY_SUBCATEGORY_ID, subcategoryId);
				contentValues.put(TabletContentProvider.KEY_NAME, itemName);
				contentValues.put(TabletContentProvider.KEY_DESCRIPTION, description);
				contentValues.put(TabletContentProvider.KEY_PICTURE_PATH, picturePath);
				contentValues.put(TabletContentProvider.KEY_PRICE, price);
				contentProviderClient.insert(
					Uri.parse(PREFIX + "/menuItem/" + menuItemId), contentValues);
			}
			
			reader.endArray();
		}
	}
	
	// Delete data currently in menuItems table
	private void deleteMenuItems(ContentProviderClient contentProviderClient)
		throws RemoteException {
		// The URI will be recognized by the content provider
		Cursor cursor = contentProviderClient.query (
			// Specify we only want the _id column
			Uri.parse(PREFIX + "/menuItem"), new String[] {TabletContentProvider.KEY_ID}, 
			null, null, null);
		if (cursor.moveToFirst()) {
			do {
				long menuItemId = cursor.getLong(0);
				contentProviderClient.delete(
					Uri.parse(PREFIX + "/menuItem/" + menuItemId),
					null, null);
			} while (cursor.moveToNext());
		}
	}
	
	// Insert data into subcategory table
	private void insertSubcategories(ContentProviderClient contentProviderClient)
		throws RemoteException, IOException {

		// TODO Access server

		URL url = new URL("http", "jdelaney.org", 8080,"/api/subcategories");
		URLConnection conn = url.openConnection();
		
		try (
			BufferedReader bufReader = new BufferedReader (
				new InputStreamReader (conn.getInputStream(), "UTF-8"));
			JsonReader reader = new JsonReader(bufReader)
		) {
			reader.beginArray();
			
			int subcategoryId = -1;
			String subcategoryName = null;
			int category = -1;
			
			while (reader.hasNext()) {
				reader.beginObject();
				
				while (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("_id")) {
						subcategoryId = reader.nextInt();
					} else if (name.equals("name")) {
						subcategoryName = reader.nextString();
					} else if (name.equals("category")) {
						category = reader.nextInt();
					}
				}
				
				reader.endObject();
				
				ContentValues contentValues = new ContentValues();
				contentValues.put(TabletContentProvider.KEY_ID, subcategoryId);
				contentValues.put(TabletContentProvider.KEY_NAME, subcategoryName);
				contentValues.put(TabletContentProvider.KEY_CATEGORY, category);
				contentProviderClient.insert(
					Uri.parse(PREFIX + "/subcategory/" + subcategoryId), contentValues);
			}
			
			reader.endArray();
		}
	}
	
	// Delete data currently in the subcategory table
	private void deleteSubcategories(ContentProviderClient contentProviderClient)
		throws RemoteException {
		// The URI will be recognized by the content provider
		Cursor cursor = contentProviderClient.query (
			// Specify we only want the _id column
			Uri.parse(PREFIX + "/subcategory"), new String[] {TabletContentProvider.KEY_ID},
			null, null, null);
		if (cursor.moveToFirst()) {
			do {
				long subcategoryId = cursor.getLong(0);
				contentProviderClient.delete (
					Uri.parse(PREFIX + "/subcategory/" + subcategoryId), 
					null, null);
			} while (cursor.moveToNext());
		}
	}

	
	// Insert data into setting table
	private void insertSettings(ContentProviderClient contentProviderClient)
			throws RemoteException, IOException {
			URL url = new URL("http", "jdelaney.org", 8080,"/api/settings");
			URLConnection conn = url.openConnection();
			
			try (
				BufferedReader bufReader = new BufferedReader (
					new InputStreamReader (conn.getInputStream(), "UTF-8"));
				JsonReader reader = new JsonReader(bufReader)
			) {
				reader.beginArray();
				
				String settingId = null;
				String value = null;
				
				while (reader.hasNext()) {
					reader.beginObject();
					
					while (reader.hasNext()) {
						String name = reader.nextName();
						if (name.equals("_id")) {
							settingId = reader.nextString();
						} else if (name.equals("value")) {
							value = reader.nextString();
						}
					}
					
					reader.endObject();
					
					ContentValues contentValues = new ContentValues();
					contentValues.put(TabletContentProvider.KEY_ID, settingId);
					contentValues.put(TabletContentProvider.KEY_VALUE, value);
					contentProviderClient.insert(
						Uri.parse(PREFIX + "/setting/" + settingId), contentValues);
				}
				
				reader.endArray();
			}
		}
		
		// Delete data currently in the setting table
		private void deleteSettings(ContentProviderClient contentProviderClient)
			throws RemoteException {
			// The URI will be recognized by the content provider
			Cursor cursor = contentProviderClient.query (
				// Specify we only want the _id column
				Uri.parse(PREFIX + "/setting"), new String[] {TabletContentProvider.KEY_ID},
				null, null, null);
			if (cursor.moveToFirst()) {
				do {
					long settingId = cursor.getLong(0);
					contentProviderClient.delete (
						Uri.parse(PREFIX + "/setting/" + settingId), 
						null, null);
				} while (cursor.moveToNext());
			}
		}
		
		// Insert data into review table
		private void insertReviews(ContentProviderClient contentProviderClient)
				throws RemoteException, IOException {
				
				URL url = new URL("http", "jdelaney.org", 8080,"/api/reviews");
				URLConnection conn = url.openConnection();
				
				try (
					BufferedReader bufReader = new BufferedReader (
						new InputStreamReader (conn.getInputStream(), "UTF-8"));
					JsonReader reader = new JsonReader(bufReader)
				) {
					reader.beginArray();
					
					int reviewId = -1;
					int menuItemId = -1;
					String reviewer = null;
					short rating = -1;
					String reviewText = null;
					String reviewDate = null;
					
					
					while (reader.hasNext()) {
						reader.beginObject();
						
						while (reader.hasNext()) {
							String name = reader.nextName();
							if (name.equals("review_id")) {
								reviewId = reader.nextInt();
							} else if (name.equals("menu_item_id")) {
								menuItemId = reader.nextInt();
							} else if (name.equals("reviewer")) {
								reviewer = reader.nextString();
							} else if (name.equals("rating")) {
								rating = (short)reader.nextInt();
							} else if (name.equals("review_text")) {
								reviewText = reader.nextString();
							} else if (name.equals("review_date")) {
								reviewDate = reader.nextString();
							}
						}
						
						reader.endObject();
						
						ContentValues contentValues = new ContentValues();
						contentValues.put(TabletContentProvider.KEY_ID, reviewId);
						contentValues.put(TabletContentProvider.KEY_MENU_ITEM_ID, menuItemId);
						contentValues.put(TabletContentProvider.KEY_REVIEWER, reviewer);
						contentValues.put(TabletContentProvider.KEY_RATING, rating);
						contentValues.put(TabletContentProvider.KEY_REVIEW_TEXT, reviewText);
						contentValues.put(TabletContentProvider.KEY_REVIEW_DATE, reviewDate);
						contentProviderClient.insert(
							Uri.parse(PREFIX + "/review/" + reviewId), contentValues);
					}
					
					reader.endArray();
				}
			}
			
			// Delete data currently in review table
			private void deleteReviews(ContentProviderClient contentProviderClient)
				throws RemoteException {
				// The URI will be recognized by the content provider
				Cursor cursor = contentProviderClient.query (
					// Specify we only want the _id column
					Uri.parse(PREFIX + "/review"), new String[] {TabletContentProvider.KEY_ID}, 
					null, null, null);
				if (cursor.moveToFirst()) {
					do {
						long reviewId = cursor.getLong(0);
						contentProviderClient.delete(
							Uri.parse(PREFIX + "/review/" + reviewId),
							null, null);
					} while (cursor.moveToNext());
				}
			}
}
