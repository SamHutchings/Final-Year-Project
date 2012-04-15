package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PresentationActivity extends Activity {

	private List<PhotoInfo> info;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation);
		
		Bundle extras = getIntent().getExtras();
		info = extras.getParcelableArrayList("Info");

	}
	
	public void onStart() {
		super.onStart();
		PhotoViewFragment viewer = (PhotoViewFragment) getFragmentManager()
				.findFragmentById(R.id.photo);
		MapViewFragment mapViewer = (MapViewFragment) getFragmentManager()
				.findFragmentById(R.id.mapview);
		
	}
}
