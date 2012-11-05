package com.whileimout.managers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.whileimout.models.Task;
import com.whileimout.utilities.FilePaths;
import com.whileimout.utilities.JSONSerializer;

import android.util.Log;

public class TaskManager {

	private static List<Task> mTasks;
	private static File mFile;

	public TaskManager(String root) {
		mFile = new File(root, FilePaths.TASKS.toString());
		if (!mFile.exists())
			try {
				mFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// Wrap exception with unchecked exception
			}
	}

	private List<Task> getListOfTasks() {
		Log.d("mFile", (mFile == null) + "");
		try {
			mTasks = (List<Task>) JSONSerializer.deserializeFromJSON(
					mFile.getAbsolutePath(), Task.class);
		} catch (IOException e) {
			Log.e("Retrieving tasks from file", e.getMessage());
		}

		return mTasks;
	}

	public synchronized List<Task> getTasks() {
		return mTasks != null ? mTasks : getListOfTasks();
	}

	public synchronized void toggleProximityModesOnAllTasks(boolean toggle) {
		for (Task t : mTasks) {
			t.setProximityMode(toggle);
		}
	}

	public synchronized void saveTasks() {
		synchronized (mTasks) {
			if (mTasks != null)
				JSONSerializer.serializeToJSON(mFile.getAbsolutePath(), mTasks);
		}
	}

	public synchronized boolean deleteTask(Task t) {
		boolean deleted = false;
		if (mTasks.contains(t)) {
			synchronized (mTasks) {
				deleted = mTasks.remove(t);
				saveTasks();
			}
		}
		return deleted;
	}

	public synchronized void deleteTask(int position) {
		try {
			deleteTask(mTasks.get(position));
		} catch (Exception e) {
		}
	}

	public synchronized Task createTask(Task t) {
		if (!mTasks.contains(t)) {
			synchronized (mTasks) {
				mTasks.add(t);
				saveTasks();
			}
		}
		return t;
	}

	public synchronized Task updateTask(Task t) {
		if (mTasks.contains(t)) {
			int index = mTasks.indexOf(t);
			mTasks.remove(index);
			mTasks.add(index, t);
			saveTasks();
		}
		return t;
	}

	public Task findTaskById(UUID id) {
		Task t = null;
		for (Task task : mTasks) {
			if (task.getId().equals(id)) {
				t = task;
				break;
			}
		}
		return t;
	}
}
