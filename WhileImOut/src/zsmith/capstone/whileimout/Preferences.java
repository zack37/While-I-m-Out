package zsmith.capstone.whileimout;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class Preferences extends SherlockPreferenceActivity {

	public static final String PREFERENCE_PROXIMITY_INTERVAL = "proximity_interval";
	public static final String PREFERENCE_TIME_INTERVAL = "time_interval";
	public static final String PREFERENCE_DISTANCE_METRIC = "distance_metric";
	public static final String PREFERENCE_NOTIFY_TASKS = "proximity_switch";
	
	public static final String DEFAULT_TIME_INTERVAl = "2";
	public static final String DEFAULT_PROXIMITY_INTERVAL = "100";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PreferencesFragment())
				.commit();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, TaskListActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, TaskListActivity.class));
		finish();
	}
}