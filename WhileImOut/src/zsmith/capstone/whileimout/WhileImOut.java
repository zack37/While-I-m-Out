package zsmith.capstone.whileimout;

import java.io.File;


import com.whileimout.managers.TaskManager;
import com.whileimout.utilities.ProximityTimer;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.preference.PreferenceManager;

public class WhileImOut extends Application {

	private static TaskManager taskManager;
	private static Context appContext;
	private static SharedPreferences preferences;
	private static Location userLocation;
	private static ProximityTimer proximityTimer;

	public static final int MILLISECONDS_IN_MINUTE = 60000;
	//Unique ID within the application
	public static final int APP_ID = 100507074;

	private static void initialize() {
		if (taskManager == null
				&& Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
			File f = appContext.getExternalFilesDir(null);
			taskManager = new TaskManager(f.getAbsolutePath());
		}
		preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
		int time = Integer.parseInt(preferences.getString("time_interval",
				Preferences.DEFAULT_TIME_INTERVAl)) * MILLISECONDS_IN_MINUTE;
		int proximity = Integer.parseInt(preferences.getString(
				Preferences.PREFERENCE_PROXIMITY_INTERVAL,
				Preferences.DEFAULT_PROXIMITY_INTERVAL));
		proximityTimer = new ProximityTimer(appContext, time, proximity);
		proximityTimer.startTimer();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		initialize();
	}

	public static void setUserLocation(Location location) {
		userLocation = location;
	}

	public static Location getUserLocation() {
		userLocation = ((LocationManager) appContext
				.getSystemService(LOCATION_SERVICE))
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (userLocation == null)
			userLocation = ((LocationManager) appContext
					.getSystemService(LOCATION_SERVICE))
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return userLocation;
	}

	public static TaskManager getTaskManager() {
		return taskManager;
	}

	public static SharedPreferences getPreferences() {
		return preferences;
	}

	public static ProximityTimer getProximityTimer() {
		return proximityTimer;
	}

	public static Context getAppContext() {
		return appContext;
	}
}
