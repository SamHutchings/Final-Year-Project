package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PresentationActivity extends Activity {

	private List<PhotoInfo> info;
	private MapViewFragment mapFragment;
	private PhotoViewFragment photoFragment;
	private int position = 0;
	private ImageView iv;
	private Bitmap bm;
	private TimerTask presentationTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation);
		
		Bundle extras = getIntent().getExtras();
		info = extras.getParcelableArrayList("Info");
		
	}
	

	public void onStart() {
		super.onStart();
		final Handler animHandler = new Handler();
		mapFragment = (MapViewFragment) getFragmentManager().findFragmentById(R.id.mapview_fragment);
		photoFragment = (PhotoViewFragment) getFragmentManager().findFragmentById(R.id.photo_fragment);
		iv = (ImageView) photoFragment.getView();
		final Runnable mUpdateResults = new Runnable() {
            public void run() {
 
                runPresentation();
 
            }
        };
 
        int delay = 1000;
        int period = 8000; 
 
        Timer timer = new Timer();
        presentationTask = new TimerTask() {
        	 
            public void run() {
     
                 animHandler.post(mUpdateResults);
     
            }
        };
     
 
        timer.scheduleAtFixedRate(presentationTask, delay, period);

	}
	
	public void onStop() {
		super.onStop();
		bm.recycle();
		presentationTask.cancel();
	}
	
	
	public void runPresentation() {
		if(position < info.size()) {
			PhotoInfo photo = info.get(position);
			bm = BitmapFactory.decodeFile(photo.getLocation());
			iv.setImageBitmap(bm);
			mapFragment.updateMapPosition(photo.getLatitude(), photo.getLongitude());
			position++;
			Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_animation);
			iv.startAnimation(fadeAnimation);
		} else {
			finish();
		}
		
		
		
	
	}
}
