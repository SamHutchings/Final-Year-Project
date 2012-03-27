package com.n0234219.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	
	   @Override    
	    protected void onCreate(Bundle bundle) {        
	        super.onCreate(bundle);
	        setContentView(R.layout.mainmenu);        
	        final Button presentationButton = (Button) findViewById(R.id.presentation);
	        presentationButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent myIntent = new Intent(MainMenuActivity.this, PresentationMenuActivity.class);
	            	 MainMenuActivity.this.startActivity(myIntent);
	             }
	         });
	        final Button exifButton = (Button) findViewById(R.id.exif);
	        exifButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent myIntent = new Intent(MainMenuActivity.this, ExifViewerActivity.class);
	            	 MainMenuActivity.this.startActivity(myIntent);
	             }
	         });
	        final Button preferencesButton = (Button) findViewById(R.id.preferences);
	        preferencesButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent myIntent = new Intent(MainMenuActivity.this, PreferencesMenuActivity.class);
	            	 MainMenuActivity.this.startActivity(myIntent);
	             }
	         });
	    }
}
