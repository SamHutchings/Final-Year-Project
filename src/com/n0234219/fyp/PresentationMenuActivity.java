package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PresentationMenuActivity extends Activity {

	private ArrayList<Uri> selectedImages;
	private TextView fileChoiceText;
	private Uri mImageCaptureUri = null;
	private List<PhotoInfo> info;
	private static final int PICK_FROM_FILE = 1;
	

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		info = new ArrayList<PhotoInfo>();
		setContentView(R.layout.presentation_menu);      
		final Button fileChoiceButton = (Button) findViewById(R.id.filechoice);
		fileChoiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PresentationMenuActivity.this, GalleryActivity.class);
				PresentationMenuActivity.this.startActivityForResult(intent, PICK_FROM_FILE);

			}
		});
		
		final Button startPresentationButton = (Button) findViewById(R.id.startpresentation);
		startPresentationButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(info.size() > 0) {
					Intent myIntent = new Intent(PresentationMenuActivity.this, PresentationActivity.class);
					myIntent.putParcelableArrayListExtra("Info",(ArrayList<? extends Parcelable>) info);
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
			if(!data.getExtras().equals(null)) {
				Bundle extras = data.getExtras();
				info = extras.getParcelableArrayList("Info");
				fileChoiceText.setText(info.size() + " files currently selected");
			}

		}
	}
	
}
