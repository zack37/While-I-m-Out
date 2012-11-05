package com.whileimout.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.whileimout.listeners.CurrentLocationListener;
import com.whileimout.models.Task;

import zsmith.capstone.whileimout.WhileImOut;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;


public class LocationProximityManager {

	private Context context;
	private double range;

	public LocationProximityManager(Context context, double range) {
		this.context = context;
		this.range = range;
	}

	public List<Task> getTasksWithinRange() {
		List<Task> tasksWithinRange = new ArrayList<Task>();
		acquireLocations();
		android.location.Location userLocation = WhileImOut.getUserLocation();
		android.location.Location current = null;
		List<Task> taskList = WhileImOut.getTaskManager().getTasks();
		for (Task t : taskList) {
			current = convertToAndroidLocation(t.getLocation());
			if (isTaskWithinRangeAndOfInterest(t.isProximityMode(), current,
					userLocation)) {
				tasksWithinRange.add(t);
			}
		}
		return tasksWithinRange;
	}

	private void acquireLocations() {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		CurrentLocationListener currentLocationListener = new CurrentLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, currentLocationListener);
	}

	public android.location.Location convertToAndroidLocation(
			com.whileimout.models.Location locationToConvert) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		android.location.Location converted = null;
		try {
			List<Address> convertedCoordinates = geocoder.getFromLocationName(
					locationToConvert.getFullyQualifiedAddress(), 1);
			if (!convertedCoordinates.isEmpty()) {
				Address a = convertedCoordinates.get(0);
				converted = new android.location.Location(
						LocationManager.PASSIVE_PROVIDER);
				converted.setLatitude(a.getLatitude());
				converted.setLongitude(a.getLongitude());
			}
		} catch (IOException ioe) {
			Toast.makeText(context, "A connection could not be established",
					Toast.LENGTH_SHORT).show();
		}
		return converted;
	}

	private boolean isTaskWithinRangeAndOfInterest(boolean taskProximityMode,
			Location taskLocation, Location userLocation) {
		return taskProximityMode && taskLocation != null
				&& userLocation != null
				&& isWithinRange(taskLocation, userLocation);
	}

	private boolean isWithinRange(Location start, Location end) {
		return start.distanceTo(end) <= range;
	}

}
