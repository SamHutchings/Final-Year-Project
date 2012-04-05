package com.n0234219.fyp;

import android.text.format.Time;

public class PhotoInfo {
	private Double latitude;
	private Double longitude;
	private String location;
	private Time timeTaken;
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double lat) {
		latitude = lat;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double lng) {
		longitude = lng;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String loc) {
		location = loc;
	}
	
	public Time getTimeTaken() {
		return timeTaken;
	}
	
	public void setTimeTaken(Time time) {
		timeTaken = time;
	}
	
	
	
}
