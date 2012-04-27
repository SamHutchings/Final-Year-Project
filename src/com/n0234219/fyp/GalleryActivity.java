package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;

import com.n0234219.fyp.GalleryFragment.GallerySelectionInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;


public class GalleryActivity extends FragmentActivity implements GalleryFragment.GallerySelectionInterface {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_fragment);
	}

	public void sendList(ArrayList<PhotoInfo> selectedInfo) {

		Intent intent = new Intent();
		intent.putParcelableArrayListExtra("Info", (ArrayList<? extends Parcelable>) selectedInfo);
		setResult(RESULT_OK, intent);
		finish();
	}

}
