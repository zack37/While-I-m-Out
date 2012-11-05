package com.whileimout.listeners;

import zsmith.capstone.whileimout.WhileImOut;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class CurrentLocationListener implements LocationListener{

	@Override
	public void onLocationChanged(Location location) {
		WhileImOut.setUserLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
