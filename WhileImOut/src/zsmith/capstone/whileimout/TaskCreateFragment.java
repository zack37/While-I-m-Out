package zsmith.capstone.whileimout;

import java.util.ArrayList;


import zsmith.capstone.whileimout.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.whileimout.managers.TaskManager;
import com.whileimout.models.Location;
import com.whileimout.models.Task;

public class TaskCreateFragment extends SherlockFragment {

	TaskManager mTaskManager;
	View mRootView;
	Task mTask;

	/************* Widgets ***************/
	Button mCreateButton;
	Button mCancelButton;
	EditText mNameEditText;
	EditText mStreetEditText;
	EditText mCityEditText;
	Spinner mStateSpinner;
	EditText mZipCodeEditText;
	ToggleButton mProximityToggleButton;
	EditText mDescriptionEditText;
	/*************************************/

	/************* Widget Text *************/
	String mTaskName;
	String mStreetAddress;
	String mCity;
	String mState;
	String mZipCode;
	boolean mProximityMode;
	String mDescription;

	/*************************************/

	int mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTaskManager = WhileImOut.getTaskManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_task_create_edit,
				container, false);

		initWidgets();
		if ((mPosition = getSherlockActivity().getIntent().getIntExtra(
				TaskDetailFragment.ARG_ITEM_ID, -1)) != -1) {
			mTask = mTaskManager.getTasks().get(mPosition);
			populateWidgets();
		}

		return mRootView;
	}

	private void populateWidgets() {
		mNameEditText.setText(mTask.getName());
		mStreetEditText.setText(mTask.getLocation().getStreetAddress());
		mCityEditText.setText(mTask.getLocation().getCity());
		mStateSpinner.setSelection(findSelectedState());
		mZipCodeEditText.setText(mTask.getLocation().getZipCode() + "");
		mProximityToggleButton.setChecked(mTask.isProximityMode());
		mDescriptionEditText.setText(mTask.getDescription());
	}

	private int findSelectedState() {
		String[] stateArray = getResources()
				.getStringArray(R.array.state_names);
		ArrayList<String> states = new ArrayList<String>();
		for (String s : stateArray) {
			states.add(s);
		}
		return states.indexOf(mTask.getLocation().getState());
	}

	private void initWidgets() {
		mNameEditText = (EditText) mRootView.findViewById(R.id.name_edit_text);
		mStreetEditText = (EditText) mRootView
				.findViewById(R.id.street_address_edit_text);
		mCityEditText = (EditText) mRootView.findViewById(R.id.city_edit_text);
		mStateSpinner = (Spinner) mRootView.findViewById(R.id.state_spinner);
		mZipCodeEditText = (EditText) mRootView
				.findViewById(R.id.zip_code_edit_text);
		mProximityToggleButton = (ToggleButton) mRootView
				.findViewById(R.id.toggle_button);
		mDescriptionEditText = (EditText) mRootView
				.findViewById(R.id.description_edit_text);
	}

	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.accept_action_bar_button:
	// if (mTask == null)
	// createNewTask();
	// else {
	// editTask();
	// }
	// break;
	// case R.id.cancel_action_bar_button:
	// cancelCreate();
	// break;
	// default:
	// return getSherlockActivity().onOptionsItemSelected(item);
	// }
	// return true;
	// }

	public void editTask() {
		if (validateFields()) {
			Location l = mTask.getLocation();
			mTask.setName(mTaskName);
			mTask.setProximityMode(mProximityMode);
			l.setStreetAddress(mStreetAddress);
			l.setCity(mCity);
			l.setState(mState);
			l.setZipCode(mZipCode);
			mTask.setLocation(l);
			mTask.setDescription(mDescription);
			mTaskManager.updateTask(mTask);
			startActivity(setExitIntent(getSherlockActivity(),
					TaskListActivity.class));
			getSherlockActivity().finish();
		} else
			notifyError();
	}

	public void createNewTask() {
		if (validateFields()) {
			Location l = new Location(mStreetAddress, mCity, mState, mZipCode);
			Task t = new Task(mTaskName, l, mProximityMode, mDescription);
			mTaskManager.createTask(t);

			Intent intent = setExitIntent(
					getActivity().getApplicationContext(),
					TaskListActivity.class);
			startActivity(intent);
			getActivity().finish();
		} else {
			notifyError();
		}
	}

	public void cancelCreate() {
		startActivity(setExitIntent(getActivity(), TaskListActivity.class));
		getActivity().finish();
	}

	private <T> Intent setExitIntent(Context context, Class<T> activity) {
		return new Intent(context, activity);
	}

	private boolean validateFields() {
		boolean returnValue = true;
		try {
			mTaskName = mNameEditText.getText().toString();

			mStreetAddress = mStreetEditText.getText().toString();
			mCity = mCityEditText.getText().toString();
			mState = mStateSpinner.getSelectedItem().toString();
			mZipCode = mZipCodeEditText.getText().toString();

			mProximityMode = mProximityToggleButton.isChecked();
			mDescription = mDescriptionEditText.getText().toString();
			returnValue = !mTaskName.isEmpty() && !mStreetAddress.isEmpty()
					&& !mCity.isEmpty() && mZipCode.length() == 5;
		} catch (NumberFormatException e) {
			if (mZipCode == "")
				returnValue = false;
		}
		return returnValue;
	}

	private void notifyError() {
		Toast.makeText(this.getActivity().getApplicationContext(),
				"Please make sure you enter correct information",
				Toast.LENGTH_LONG).show();
	}

}
