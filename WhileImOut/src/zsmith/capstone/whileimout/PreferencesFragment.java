package zsmith.capstone.whileimout;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.text.InputType;

import com.whileimout.utilities.DistanceMetrics;
import com.whileimout.utilities.ProximityTimer;

public class PreferencesFragment extends PreferenceFragment {

	private DistanceMetrics distanceMetric;
	private SwitchPreference notifyTasks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		setUpPreferences();
	}

	private void setUpPreferences() {
		setUpDistanceMetricPreference();
		setUpTimeIntervalPreference();
		setUpProximityIntervalPreference();
		setUpNotifyTasksPreference();
	}

	private void setUpNotifyTasksPreference() {
		notifyTasks = (SwitchPreference) findPreference(Preferences.PREFERENCE_NOTIFY_TASKS);
		boolean isOn = notifyTasks.isChecked();
		changeNotifyTaskIcon(isOn);
		notifyTasks
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						boolean toggle = newValue.toString().equals("true");
						changeNotifyTaskIcon(toggle);
						WhileImOut.getTaskManager()
								.toggleProximityModesOnAllTasks(toggle);
						return true;
					}
				});
	}

	private void changeNotifyTaskIcon(boolean isChecked) {
		notifyTasks.setIcon(isChecked ? R.drawable.ic_preferences_notify_on
				: R.drawable.ic_preferences_notify_off);
	}

	private void setUpDistanceMetricPreference() {
		ListPreference distanceMetricPreference = (ListPreference) findPreference(Preferences.PREFERENCE_DISTANCE_METRIC);
		distanceMetric = DistanceMetrics.tryParse(distanceMetricPreference
				.getValue());
		distanceMetricPreference.setEntries(DistanceMetrics.getEntries());
		distanceMetricPreference.setEntryValues(DistanceMetrics.getEntries());
		distanceMetricPreference
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						distanceMetric = DistanceMetrics.tryParse(newValue
								.toString());
						changeProximityIntervalSummary(distanceMetric
								.toString());
						return true;
					}
				});
	}

	private void setUpTimeIntervalPreference() {
		EditTextPreference timeIntervalPreference = (EditTextPreference) findPreference(Preferences.PREFERENCE_TIME_INTERVAL);
		timeIntervalPreference
				.setDefaultValue(Preferences.DEFAULT_TIME_INTERVAl);
		timeIntervalPreference.getEditText().setInputType(
				InputType.TYPE_CLASS_NUMBER);
		timeIntervalPreference
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						int intervalInMinutes = Integer
								.parseInt((String) newValue);
						WhileImOut.getProximityTimer().changeInterval(
								intervalInMinutes
										* WhileImOut.MILLISECONDS_IN_MINUTE);
						return true;
					}
				});
	}

	private double convertDistanceMetricToMeters(double userPreference) {
		return userPreference * distanceMetric.getConversionValue();
	}

	private String proximitySummary = "Please select the distance (in $) of how far away you wish to be before While I'm Out will notify you";
	EditTextPreference proximityIntervalPreference;

	private void setUpProximityIntervalPreference() {
		proximityIntervalPreference = (EditTextPreference) findPreference(Preferences.PREFERENCE_PROXIMITY_INTERVAL);
		changeProximityIntervalSummary(distanceMetric.toString());
		proximityIntervalPreference
				.setDefaultValue(Preferences.DEFAULT_PROXIMITY_INTERVAL);
		proximityIntervalPreference.getEditText().setInputType(
				InputType.TYPE_CLASS_NUMBER);
		proximityIntervalPreference
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						double convertedDistance = convertDistanceMetricToMeters(Integer
								.parseInt(newValue.toString()));
						ProximityTimer pTimer = WhileImOut.getProximityTimer();
						pTimer.setDistance(convertedDistance);
						pTimer.startTimer();
						return true;
					}
				});
	}

	private void changeProximityIntervalSummary(String metric) {
		String newSummary = proximitySummary.replace("$", metric);
		proximityIntervalPreference.setSummary(newSummary);
	}
}
