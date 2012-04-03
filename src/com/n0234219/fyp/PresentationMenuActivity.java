package com.n0234219.fyp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PresentationMenuActivity extends Activity {

	private ArrayList<Uri> selectedImages;
	private TextView fileChoiceText;
	private Uri mImageCaptureUri = null;
	private String filePath = null;
	private static final int PICK_FROM_FILE = 1;

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
				if(null != filePath) {
					Intent myIntent = new Intent(PresentationMenuActivity.this, PresentationActivity.class);
					myIntent.putExtra("Path", filePath);
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
				filePath = getRealPathFromURI(mImageCaptureUri);
				fileChoiceText.setText(filePath + " selected.");

			}

		}
	}
	
	  public String getRealPathFromURI(Uri contentUri) {
	        String [] proj      = {MediaStore.Images.Media.DATA};
	        Cursor cursor       = managedQuery( contentUri, proj, null, null,null);
	 
	        if (cursor == null) return null;
	 
	        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	 
	        cursor.moveToFirst();
	 
	        return cursor.getString(column_index);
	    }
}
