package models;

public class Location {

	private String streetAddress;
	private String state;
	private String city;
	private int zipCode;

	public Location() {
	}

	public Location(String streetAddress, String state, String city, int zip) {
		this.streetAddress = streetAddress;
		this.state = state;
		this.city = city;
		zipCode = zip;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
}
