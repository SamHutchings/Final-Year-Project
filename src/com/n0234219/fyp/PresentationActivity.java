package com.n0234219.fyp;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class PresentationActivity extends FragmentActivity {

	private List<PhotoInfo> info;
	private MapViewFragment mapFragment;
	private PhotoViewFragment photoFragment;
	private int position = 0;
	private int delay;
	private int startDelay;
	private ImageView iv;
	private Bitmap bm;
	private TimerTask presentationTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presentation);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		try {
			delay = Integer.parseInt(prefs.getString("delay_preference", "4"));
			delay = delay*1000;
		} catch (NumberFormatException ex) {
			Toast.makeText(getApplicationContext(), "Delay preference value invalid. Delay set to 4 seconds.", Toast.LENGTH_SHORT).show();
			delay = 4000;
		}
		startDelay = 1000;
		Bundle extras = getIntent().getExtras();
		info = extras.getParcelableArrayList("Info");
		
	}
	

	public void onStart() {
		super.onStart();
		final Handler animHandler = new Handler();
		mapFragment = (MapViewFragment) getSupportFragmentManager().findFragmentById(R.id.mapview_fragment);
		photoFragment = (PhotoViewFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fragment);
		iv = (ImageView) findViewById(R.id.photo);
		final Runnable mUpdateResults = new Runnable() {
            public void run() {
 
                runPresentation();
 
            }
        };
        
 
        Timer timer = new Timer();
        presentationTask = new TimerTask() {
        	 
            public void run() {
     
                 animHandler.post(mUpdateResults);
     
            }
        };
     
 
        timer.scheduleAtFixedRate(presentationTask, startDelay, delay);

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
