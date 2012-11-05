package com.whileimout.utilities;

public enum DistanceMetrics {
	FEET("feet", "Feet", .3048), MILES("miles", "Miles", 1609.344), METERS("meters", "Meters", 1), KILOMETERS(
			"kilometers", "Kilometers", 1000);
	private String lowerCaseName;
	private String camelCase;
	private double conversionValue;

	DistanceMetrics(String lowerCaseName, String camelCase, double conversionValue) {
		this.lowerCaseName = lowerCaseName;
		this.camelCase = camelCase;
		this.conversionValue = conversionValue;
	}

	@Override
	public String toString() {
		return lowerCaseName;
	}

	private String getCamelCase() {
		return camelCase;
	}

	public static DistanceMetrics tryParse(String nameOfEnum) {
		DistanceMetrics returnValue = null;
		DistanceMetrics[] distanceMetrics = values();
		for (DistanceMetrics dm : distanceMetrics) {
			if (nameOfEnum.toUpperCase().equals(dm.name()))
				returnValue = dm;
		}
		return returnValue;
	}

	public static String[] getEntries() {
		String[] returnValues = new String[values().length];
		for (int i = 0; i < values().length; i++) {
			returnValues[i] = values()[i].getCamelCase();
		}
		return returnValues;
	}
	
	public double getConversionValue(){
		return conversionValue;
	}

}
