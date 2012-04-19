package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
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
		final Runnable mUpdateResults = new Runnable() {
            public void run() {
 
                runPresentation();
 
            }
        };
 
        int delay = 1000; // delay for 1 sec.
 
        int period = 8000; // repeat every 4 sec.
 
        Timer timer = new Timer();
 
        timer.scheduleAtFixedRate(new TimerTask() {
 
        public void run() {
 
             animHandler.post(mUpdateResults);
 
        }
 
        }, delay, period);

	}
	
	public void runPresentation() {
		PhotoInfo photo = info.get(position);
		iv = (ImageView) findViewById(R.id.photo);
		iv.setImageBitmap(bm);
		position++;
		Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
		mapFragment.updateMapPosition(photo.getLatitude(), photo.getLongitude());
		
	
	}
}
