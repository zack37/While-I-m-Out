package zsmith.capstone.whileimout;

import zsmith.capstone.whileimout.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.whileimout.models.Location;
import com.whileimout.models.Task;
import com.whileimout.utilities.GoogleMaps;

public class TaskDetailFragment extends SherlockFragment {

	public static final String ARG_ITEM_ID = "item_id";

	Task mTask;
	Location mLocation;
	View mRootView;
	ImageView mMapView;
	LinearLayout mProgressBarLayout;

	public TaskDetailFragment() {
	}

	/*
	 * Generic Parameters: Void - Sent upon Execution. Void - Type when
	 * incrementing. Void - Result of background computation.
	 */
	private class LoadingView extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			DisplayMetrics dm = new DisplayMetrics();
			getSherlockActivity().getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			GoogleMaps.getGoogleMapBitmap(dm.heightPixels, dm.widthPixels,
					mTask.getLocation(), mMapView);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mMapView.setVisibility(View.VISIBLE);
			mProgressBarLayout.setVisibility(View.GONE);

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			mTask = WhileImOut.getTaskManager().getTasks().get(
					getArguments().getInt(ARG_ITEM_ID));
			mLocation = mTask.getLocation();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_task_detail, container,
				false);
		mMapView = (ImageView) mRootView.findViewById(R.id.imageView);
		mProgressBarLayout = (LinearLayout) mRootView
				.findViewById(R.id.progress_bar_layout);
		if (mTask != null) {
			getSherlockActivity().setTitle("Details about: " + mTask.getName());
			populateViewWidgets();
		}
		return mRootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		new LoadingView().execute();
	}

	private void populateViewWidgets() {

		((TextView) mRootView.findViewById(R.id.task_detail)).setText(mTask
				.getName());
		((TextView) mRootView.findViewById(R.id.streetAddressTextView))
				.setText(mLocation.getStreetAddress());
		((TextView) mRootView.findViewById(R.id.cityTextView))
				.setText(mLocation.getCity());
		((TextView) mRootView.findViewById(R.id.stateTextView))
				.setText(mLocation.getState());
		((TextView) mRootView.findViewById(R.id.zipCodeTextView))
				.setText(mLocation.getZipCode() + "");
		((ToggleButton) mRootView.findViewById(R.id.isProximityModeButton))
				.setChecked(mTask.isProximityMode());
		((TextView) mRootView.findViewById(R.id.descriptionTextView))
				.setText(mTask.getDescription());

	}

}