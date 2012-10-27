package es.finnapps.piggybank.services;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService{

    @Override
    protected void onError(Context arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onMessage(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onRegistered(Context arg0, String registrationId) {
        // TODO Auto-generated method stub
        Log.d("REGISTERED","registered with: " + registrationId);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

}
