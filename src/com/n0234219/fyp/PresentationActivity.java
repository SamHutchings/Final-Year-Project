package com.n0234219.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PresentationActivity extends Activity {

	private String fileName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation);
		PhotoViewFragment viewer = (PhotoViewFragment) getFragmentManager()
				.findFragmentById(R.id.photo);

		if (viewer == null || !viewer.isInLayout()) {
			Intent showContent = new Intent(getApplicationContext(),
					MapViewActivity.class);
			
			startActivity(showContent);	

		} else {
			
		}

	}
}
