package org.segfault.foodme;

import java.io.IOException;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

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
		} catch (RemoteException | IOException e) {
			syncResult.hasHardError();
		}
	}
	
	private void insertMenuItems(ContentProviderClient contentProviderClient)
		throws RemoteException, IOException {
		// TODO Access server
	}
	
	// Delete data currently in menuItems table
	private void deleteMenuItems(ContentProviderClient contentProviderClient)
		throws RemoteException {
		/*// The URI will be recognized by the content provider
		Cursor cursor = contentProviderClient.query (
			Uri.parse(PREFIX + "/menuItem"), new String[] {menuItem._id}, 
			null, null, null);
		if (cursor.moveToFirst()) {
			do {
				long menuItemId = cursor.getLong(0);
				contentProviderClient.delete(
					Uri.parse(PREFIX + "/menuItem/" + menuItemId),
					null, null);
			} while (cursor.moveToNext());
		}*/
	}
	
	private void insertSubcategories(ContentProviderClient contentProviderClient)
		throws RemoteException, IOException {
		// TODO Access server
	}
	
	// Delete data currently in the subcategories table
	private void deleteSubcategories(ContentProviderClient contentProviderClient)
		throws RemoteException {
		/*// The URI will be recognized by the content provider
		Cursor cursor = contentProviderClient.query (
			Uri.parse(PREFIX + "/subcategory"), new String[] {subcategory._id},
			null, null, null);
		if (cursor.moveToFirst()) {
			do {
				long subcategoryId = cursor.getLong(0);
				contentProviderClient.delete (PREFIX + "/subcategory/" + subcategoryId, 
					null, null);
			} while (cursor.moveToNext());
		}*/
	}
}
