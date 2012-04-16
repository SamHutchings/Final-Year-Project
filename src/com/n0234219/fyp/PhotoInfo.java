package com.n0234219.fyp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

public class PhotoInfo implements Parcelable {
	private Double latitude;
	private Double longitude;
	private String location;
	private Long timeTaken;
	
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
	
	public long getTimeTaken() {
		return timeTaken;
	}
	
	public void setTimeTaken(long time) {
		timeTaken = time;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(location);
		dest.writeLong(timeTaken);	
	}
	
    public static final Parcelable.Creator<PhotoInfo> CREATOR = new Parcelable.Creator<PhotoInfo>() {
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    private PhotoInfo(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        location = in.readString();
        timeTaken = in.readLong();
    }

	public PhotoInfo() {
	}
	
	
	
}
