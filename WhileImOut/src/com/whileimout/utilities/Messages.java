package com.whileimout.utilities;

import java.util.Date;

public enum Messages {
	CREATE("Task was created"), UPDATE("Task was updated"), DELETE("Task was deleted"), ERROR("Something went wrong");
	
	private String message;
	
	private Messages(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message + " on " + new Date();
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
