package com.n0234219.fyp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PresentationMenuActivity extends Activity {
	
	private ArrayList<Uri> selectedImages;
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_FILE = 2;
	
	@Override
	public void onCreate(Bundle bundle) {
	 super.onCreate(bundle);
     setContentView(R.layout.presentation_menu);      
     final Button fileChoiceButton = (Button) findViewById(R.id.filechoice);
     fileChoiceButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intent = new Intent();
        	  
              intent.setType("image/*");
              intent.setAction(Intent.ACTION_GET_CONTENT);

              startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
          }
      });
     final Button startPresentationButton = (Button) findViewById(R.id.startpresentation);
     startPresentationButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
         	 Intent myIntent = new Intent(PresentationMenuActivity.this, PresentationActivity.class);
         	 PresentationMenuActivity.this.startActivity(myIntent);
          }
      });
     final TextView fileChoiceText = (TextView) findViewById(R.id.filechoicetext);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
 
       if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            
        }

	}
}
