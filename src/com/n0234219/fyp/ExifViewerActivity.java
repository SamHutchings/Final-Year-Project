package com.n0234219.fyp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;


public class ExifViewerActivity extends FragmentActivity implements ThumbnailViewFragment.OnImageSelectedListener {
	
	@Override    
	protected void onCreate(Bundle bundle) {        
		super.onCreate(bundle);
		setContentView(R.layout.exif_viewer);        
	}

	public void onImageSelected(Double lat, Double lng) {
		MapViewFragment viewer = (MapViewFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapview_fragment);

		if (viewer == null || !viewer.isInLayout()) {
			Intent showContent = new Intent(getApplicationContext(),
					MapViewActivity.class);
			showContent.putExtra("latitude", lat);
			showContent.putExtra("longitude", lng);
			startActivity(showContent);	

		} else {
			viewer.updateMapPosition(lat, lng);
		}


	}
}
