package org.segfault.foodme;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service{
	
	// Instance field that stores the authenticator object
	private Authenticator myAuthenticator;
	
	@Override
	public void onCreate() {
		// Create new authenticator object
		myAuthenticator = new Authenticator(this);
	}
	
	// When the system binds to this Service to make the RPC call
	// return the authenticator's IBinder
	@Override
	public IBinder onBind(Intent intent) {
		return myAuthenticator.getIBinder();
	}
}
