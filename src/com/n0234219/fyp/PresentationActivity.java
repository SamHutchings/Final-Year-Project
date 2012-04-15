package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PresentationActivity extends Activity {

	private List<PhotoInfo> info;
	private MapViewFragment mapFragment;
	private PhotoViewFragment photoFragment;
	private ImageView iv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation);
		
		Bundle extras = getIntent().getExtras();
		info = extras.getParcelableArrayList("Info");

	}
	
	public void onStart() {
		super.onStart();
		photoFragment = (PhotoViewFragment) getFragmentManager()
				.findFragmentById(R.id.photo_fragment);
		mapFragment = (MapViewFragment) getFragmentManager()
				.findFragmentById(R.id.mapview_fragment);
		runPresentation();
		
	}
	
	
	
	public void runPresentation() {
		for(PhotoInfo photo : info) {
			photoFragment.updateImage(photo.getLocation());
			mapFragment.updateMapPosition(photo.getLatitude(), photo.getLongitude());
		}
	
	}
}
