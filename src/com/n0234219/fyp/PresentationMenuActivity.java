package com.n0234219.fyp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PresentationMenuActivity extends Activity {

	private ArrayList<Uri> selectedImages;
	private TextView fileChoiceText;
	private Uri mImageCaptureUri = null;
	private PhotoInfo info = null;
	private static final int PICK_FROM_FILE = 1;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		info = new PhotoInfo();
		setContentView(R.layout.presentation_menu);      
		final Button fileChoiceButton = (Button) findViewById(R.id.filechoice);
		fileChoiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PresentationMenuActivity.this, GalleryActivity.class);
				PresentationMenuActivity.this.startActivity(intent);
// Intent myIntent = new Intent(MainMenuActivity.this, PresentationMenuActivity.class);
           	 
//				intent.setType("image/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//
//				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
			}
		});
		
		final Button startPresentationButton = (Button) findViewById(R.id.startpresentation);
		startPresentationButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(null != info.getLocation()) {
					Intent myIntent = new Intent(PresentationMenuActivity.this, PresentationActivity.class);
					myIntent.putExtra("Info", info);
					PresentationMenuActivity.this.startActivity(myIntent);
				}
			}
		});
		fileChoiceText = (TextView) findViewById(R.id.filechoicetext);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) return;

		if (requestCode == PICK_FROM_FILE) {
			if(!data.getData().equals(null)) {
				mImageCaptureUri = data.getData();
				info = setPhotoInfo(mImageCaptureUri);
				fileChoiceText.setText(info.getLocation() + " selected.");

			}

		}
	}
	
	  public PhotoInfo setPhotoInfo(Uri contentUri) {
	        String [] proj      = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.LATITUDE,
	        		MediaStore.Images.Media.LONGITUDE, MediaStore.Images.Media.DATE_TAKEN};
	        Cursor cursor       = managedQuery( contentUri, proj, null, null,null);
	        PhotoInfo newInfo = new PhotoInfo();
	        if (cursor == null) return null;
	        cursor.moveToFirst();
	 
	        newInfo.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
	        newInfo.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE)));
	        newInfo.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE)));
	        newInfo.setTimeTaken(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)));
	 
	        return newInfo;
	    }
}
