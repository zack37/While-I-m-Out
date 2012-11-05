package com.whileimout.utilities;



import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.whileimout.models.Task;

public class GsonExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes fa) {
		return fa.getDeclaringClass() == Task.class
				&& fa.getName().contains("pinpoint");
	}

}